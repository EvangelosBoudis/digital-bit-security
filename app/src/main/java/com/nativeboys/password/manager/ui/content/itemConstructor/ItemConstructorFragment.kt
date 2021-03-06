package com.nativeboys.password.manager.ui.content.itemConstructor

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.Result
import com.nativeboys.password.manager.data.TagDto
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.password.manager.databinding.FragmentItemConstructorBinding
import com.nativeboys.password.manager.util.*
import com.nativeboys.password.manager.presentation.ItemConstructorViewModel
import com.nativeboys.password.manager.ui.adapters.fieldContent.FieldContentAdapter
import com.nativeboys.password.manager.ui.adapters.tags.TagsAdapter
import com.nativeboys.password.manager.ui.adapters.thumbnails.ThumbnailsAdapter
import com.nativeboys.password.manager.presentation.ItemConstructorViewModel.Companion.NOTES
import com.nativeboys.password.manager.presentation.ItemConstructorViewModel.Companion.PASSWORD_REQUIRED
import com.nativeboys.password.manager.ui.adapters.FieldTextChangeListener
import com.nativeboys.password.manager.ui.content.confirmation.ConfirmationFragment
import com.nativeboys.password.manager.ui.content.itemConstructor.bottomFragment.TagFactoryBottomFragment
import com.nativeboys.password.manager.ui.content.itemConstructor.bottomFragment.ThumbnailFactoryBottomFragment
import com.nativeboys.uikit.rv.AdapterClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemConstructorFragment : Fragment(
    R.layout.fragment_item_constructor
), View.OnClickListener {

    private val viewModel: ItemConstructorViewModel by viewModels()

    private lateinit var fieldsAdapter: FieldContentAdapter
    private lateinit var tagsAdapter: TagsAdapter
    private lateinit var thumbnailsAdapter: ThumbnailsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentItemConstructorBinding.bind(view)
        fieldsAdapter = FieldContentAdapter(object : FieldTextChangeListener {
            override fun onChange(fieldId: String, textContent: String) {
                viewModel.updateUserCache(fieldId, textContent)
            }
        })
        thumbnailsAdapter = ThumbnailsAdapter()
        tagsAdapter = TagsAdapter()

        binding.apply {
            fieldsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            fieldsRecyclerView.adapter = fieldsAdapter
            headerContainer.headlineField.setText(R.string.edit_item)
            thumbnailsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            thumbnailsRecyclerView.adapter = thumbnailsAdapter
            val layoutManager = FlexboxLayoutManager(requireContext())
            layoutManager.wrapCells()
            tagsRecyclerView.layoutManager = layoutManager
            tagsRecyclerView.adapter = tagsAdapter

            headerContainer.leadingBtn.setOnClickListener(this@ItemConstructorFragment)
            headerContainer.trailingBtn.setOnClickListener(this@ItemConstructorFragment)
            notesField.addTextChangedListener {
                val text = it?.toString() ?: ""
                viewModel.updateUserCache(NOTES, text)
            }
            passwordSwitch.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateUserCache(PASSWORD_REQUIRED, isChecked)
            }
            thumbnailsAdapter.adapterClickListener = object : AdapterClickListener<ThumbnailDto> {

                override fun onClick(view: View, model: ThumbnailDto, position: Int) {
                    when (model.type) {
                        1 -> {
                           viewModel.selectThumbnail(model)
                        }
                        3 -> {
                            showThumbnailBottomFactory(model)
                        }
                    }
                }

                override fun onLongClick(view: View, model: ThumbnailDto, position: Int) {
                    showThumbnailBottomFactory(model)
                }

            }
            tagsAdapter.adapterClickListener = object : AdapterClickListener<TagDto> {

                override fun onClick(view: View, model: TagDto, position: Int) {
                    showTagFactoryBottomFragment(model)
                }

            }
        }

        viewModel.getInitFieldsContent().observe(viewLifecycleOwner) {
            fieldsAdapter.submitList(it)
        }
        viewModel.observeThumbnails.observe(viewLifecycleOwner) {
            thumbnailsAdapter.submitList(it)
        }
        viewModel.observeTags.observe(viewLifecycleOwner) {
            tagsAdapter.submitList(it)
        }
        viewModel.getInitPasswordIsRequired().observe(viewLifecycleOwner) {
            binding.passwordSwitch.isChecked = it
        }
        viewModel.getInitNotes().observe(viewLifecycleOwner) {
            binding.notesField.setText(it)
        }
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.trailing_btn -> {
                view.isEnabled = false
                viewModel.submitItem().observe(viewLifecycleOwner) { result ->
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
            R.id.leading_btn -> {
                activity?.onBackPressed()
            }
        }
    }

    private fun showTagFactoryBottomFragment(tagDto: TagDto) =
        TagFactoryBottomFragment
            .newInstance(tagDto)
            .show(childFragmentManager, TagFactoryBottomFragment::class.java.simpleName)

    private fun showThumbnailBottomFactory(thumbnailDto: ThumbnailDto) =
        ThumbnailFactoryBottomFragment
            .newInstance(thumbnailDto)
            .show(childFragmentManager, ThumbnailFactoryBottomFragment::class.java.simpleName)

}