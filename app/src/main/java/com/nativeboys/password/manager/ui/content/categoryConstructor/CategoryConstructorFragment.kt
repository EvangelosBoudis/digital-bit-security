package com.nativeboys.password.manager.ui.content.categoryConstructor

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.Result
import com.nativeboys.password.manager.data.UIField
import com.nativeboys.password.manager.databinding.FragmentCategoryConstructorBinding
import com.nativeboys.password.manager.presentation.CategoryConstructorViewModel
import com.nativeboys.password.manager.presentation.CategoryConstructorViewModel.Companion.NAME
import com.nativeboys.password.manager.presentation.CategoryConstructorViewModel.Companion.PENDING_FIELD_ID_FOR_TYPE
import com.nativeboys.password.manager.ui.adapters.FieldTextChangeListener
import com.nativeboys.password.manager.ui.adapters.newFields.NewFieldsAdapter
import com.nativeboys.password.manager.ui.content.confirmation.ConfirmationFragment
import com.nativeboys.password.manager.ui.content.home.items.SettingsBottomFragment
import com.nativeboys.password.manager.util.ZTransactionFragment
import com.nativeboys.password.manager.util.intoMaterialIcon
import com.nativeboys.password.manager.util.materialIconCodeToDrawable
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryConstructorFragment : ZTransactionFragment(
    R.layout.fragment_category_constructor
), View.OnClickListener, AdapterClickListener<UIField> {

    private val viewModel: CategoryConstructorViewModel by viewModels()

    private lateinit var fieldsAdapter: NewFieldsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCategoryConstructorBinding.bind(view)
        fieldsAdapter = NewFieldsAdapter(object : FieldTextChangeListener {
            override fun onChange(fieldId: String, textContent: String) {
                viewModel.updateFieldName(fieldId, textContent)
            }
        })
        binding.apply {
            headerContainer.headlineField.setText(R.string.create_category)
            fieldsRecyclerView.layoutManager = LinearLayoutManager(view.context)
            fieldsRecyclerView.adapter = fieldsAdapter
            headerContainer.leadingBtn.setOnClickListener(this@CategoryConstructorFragment)
            headerContainer.trailingBtn.setOnClickListener(this@CategoryConstructorFragment)
            thumbnailHolder.setOnClickListener(this@CategoryConstructorFragment)
            contentField.addTextChangedListener {
                viewModel.updateUserCache(NAME, it?.toString())
            }
        }
        fieldsAdapter.adapterClickListener = this
        /*We use asLiveData() here and not at presentation layer as work around, because map transformation not getting triggered.
        * For further details go to asLiveData() function documentation */
        viewModel.observeFields.asLiveData().observe(viewLifecycleOwner) {
            fieldsAdapter.submitList(it)
        }
        viewModel.getInitName().observe(viewLifecycleOwner) {
            binding.contentField.setText(it)
        }
        viewModel.observeThumbnailCode.observe(viewLifecycleOwner) { thumbnailCode ->
            thumbnailCode?.let { code ->
                val draw = materialIconCodeToDrawable(
                    requireContext(),
                    code,
                    ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                    36
                )
                if (draw != null) {
                    Glide.with(requireContext())
                        .load(draw)
                        .transition(DrawableTransitionOptions().crossFade())
                        .intoMaterialIcon(binding.thumbnailHolder)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.leading_btn -> {
                activity?.onBackPressed()
            }
            R.id.trailing_btn -> {
                view.isEnabled = false
                viewModel.submit().observe(viewLifecycleOwner) { result ->
                    view.isEnabled = true
                    when (result) {
                        is Result.Success -> activity?.onBackPressed()
                        is Result.Error -> {
                            ConfirmationFragment
                                .newInstance(R.layout.dialog_error, getString(R.string.form_validation_error), result.exception.message)
                                .show(childFragmentManager, ConfirmationFragment::class.java.simpleName)
                        }
                    }
                }
            }
            R.id.thumbnail_holder -> {
                CategoryIconChooserBottomFragment().show(
                    this.childFragmentManager,
                    SettingsBottomFragment::class.java.simpleName
                )
            }
        }
    }

    override fun onClick(view: View, model: UIField, position: Int) {
        if (model.cellType == 2) {
            viewModel.addNewField()
        } else {
            when (view.id) {
                R.id.type_btn -> {
                    viewModel.updateUserCache(PENDING_FIELD_ID_FOR_TYPE, model.id)
                    CategoryTypeChooserBottomFragment().show(
                        this.childFragmentManager,
                        CategoryTypeChooserBottomFragment::class.java.simpleName
                    )
                }
                R.id.delete_btn -> {
                    viewModel.deleteField(model.id)
                }
            }
        }
    }

}