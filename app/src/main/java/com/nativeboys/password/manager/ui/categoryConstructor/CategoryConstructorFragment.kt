package com.nativeboys.password.manager.ui.categoryConstructor

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldData
import com.nativeboys.password.manager.databinding.FragmentCategoryConstructorBinding
import com.nativeboys.password.manager.presentation.CategoryConstructorViewModel
import com.nativeboys.password.manager.ui.adapters.newFields.NewFieldsAdapter
import com.nativeboys.password.manager.ui.home.items.SettingsBottomFragment
import com.nativeboys.password.manager.util.intoMaterialIcon
import com.nativeboys.password.manager.util.materialIconCodeToDrawable
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryConstructorFragment : Fragment(
    R.layout.fragment_category_constructor
), View.OnClickListener, AdapterClickListener<FieldData> {

    private val viewModel: CategoryConstructorViewModel by viewModels()

    private lateinit var fieldsAdapter: NewFieldsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCategoryConstructorBinding.bind(view)
        fieldsAdapter = NewFieldsAdapter()
        binding.apply {
            headerContainer.headlineField.setText(R.string.create_category)
            fieldsRecyclerView.layoutManager = LinearLayoutManager(view.context)
            fieldsRecyclerView.adapter = fieldsAdapter
            headerContainer.leadingBtn.setOnClickListener(this@CategoryConstructorFragment)
            headerContainer.trailingBtn.setOnClickListener(this@CategoryConstructorFragment)
            thumbnailHolder.setOnClickListener(this@CategoryConstructorFragment)
        }
        fieldsAdapter.adapterClickListener = this
        viewModel.fields.observe(viewLifecycleOwner) {
            fieldsAdapter.dataSet = it
        }
        viewModel.getInitName().observe(viewLifecycleOwner) {
            binding.contentField.setText(it)
        }
        viewModel.thumbnailCode.observe(viewLifecycleOwner) { thumbnailCode ->
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
                // TODO: implement
            }
            R.id.thumbnail_holder -> {
                CategoryIconChooserBottomFragment().show(
                    this.childFragmentManager,
                    SettingsBottomFragment::class.java.simpleName
                )
            }
        }
    }

    override fun onClick(view: View, model: FieldData, position: Int) {
        if (view.id == R.id.type_btn) {
            CategoryTypeChooserBottomFragment().show(
                this.childFragmentManager,
                CategoryTypeChooserBottomFragment::class.java.simpleName
            )
        }
    }

}