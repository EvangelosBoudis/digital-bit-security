package com.nativeboys.password.manager.ui.itemConstructor

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.TagDto
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.password.manager.databinding.FragmentItemConstructorBinding
import com.nativeboys.password.manager.other.*
import com.nativeboys.password.manager.ui.adapters.fieldContent.FieldContentAdapter
import com.nativeboys.password.manager.ui.adapters.fieldContent.FieldContentTextChangeListener
import com.nativeboys.password.manager.ui.adapters.tags.TagsAdapter
import com.nativeboys.password.manager.ui.adapters.thumbnails.OnThumbnailLongClickListener
import com.nativeboys.password.manager.ui.adapters.thumbnails.ThumbnailsAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ItemConstructorFragment : Fragment(R.layout.fragment_item_constructor), View.OnClickListener {

    private val viewModel: ItemConstructorViewModel by viewModels()

    private lateinit var fieldsAdapter: FieldContentAdapter
    private lateinit var tagsAdapter: TagsAdapter
    private lateinit var thumbnailsAdapter: ThumbnailsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentItemConstructorBinding.bind(view)
        fieldsAdapter = FieldContentAdapter(object : FieldContentTextChangeListener {
            override fun onContentChanged(contentId: String, textContent: String) {
                viewModel.updateUserCache(contentId, textContent)
            }
        })
        thumbnailsAdapter = ThumbnailsAdapter(object : OnThumbnailLongClickListener {
            override fun onLongClick(model: ThumbnailDto) {
                showBottomFragment(thumbnailDto = model)
            }
        })
        tagsAdapter = TagsAdapter()

        binding.apply {
            fieldsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            fieldsRecyclerView.adapter = fieldsAdapter
            headerContainer.headlineField.setText(R.string.create_item)
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
                viewModel.updateUserCache(NOTES_ID, text)
            }
            thumbnailsAdapter.adapterClickListener = object : AdapterClickListener<ThumbnailDto> {
                override fun onClick(view: View, model: ThumbnailDto, position: Int) {
                    when (model.type) {
                        1 -> {
                           viewModel.selectThumbnail(model.id)
                        }
                        3 -> {
                            showBottomFragment(thumbnailDto = model)
                        }
                    }
                }
            }
            tagsAdapter.adapterClickListener = object : AdapterClickListener<TagDto> {
                override fun onClick(view: View, model: TagDto, position: Int) {
                    if (view.id == R.id.remove_btn) {
                        viewModel.deleteTag(model.name)
                    } else {
                        showBottomFragment(tagDto = model)
                    }
                }
            }
        }
        viewModel.transformedFieldContent.asLiveData().observe(viewLifecycleOwner) {
            fieldsAdapter.submitList(it)
        }
        viewModel.thumbnails.asLiveData().observe(viewLifecycleOwner) {
            thumbnailsAdapter.submitList(it)
        }
        viewModel.tags.asLiveData().observe(viewLifecycleOwner) {
            tagsAdapter.submitList(it)
        }
        viewModel.passwordIsRequired.asLiveData().observe(viewLifecycleOwner) {
            binding.passwordSwitch.isChecked = it
        }
        viewModel.notes.asLiveData().observe(viewLifecycleOwner) {
            binding.notesField.setText(it)
        }
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.submit_btn -> {
                // TODO: implement
            }
            R.id.leading_btn -> {
                activity?.onBackPressed()
            }
        }
    }

    private fun showBottomFragment(tagDto: TagDto? = null, thumbnailDto: ThumbnailDto? = null) {
        FactoryBottomFragment
            .newInstance(tagDto, thumbnailDto)
            .show(childFragmentManager, FactoryBottomFragment::class.java.simpleName)
    }

}