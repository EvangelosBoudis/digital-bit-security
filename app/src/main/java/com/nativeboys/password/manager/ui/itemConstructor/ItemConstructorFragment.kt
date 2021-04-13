package com.nativeboys.password.manager.ui.itemConstructor

import android.os.Bundle
import android.view.View
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
import com.nativeboys.password.manager.ui.adapters.tags.TagsAdapter
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
        fieldsAdapter = FieldContentAdapter()
        thumbnailsAdapter = ThumbnailsAdapter()
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
            thumbnailsAdapter.adapterClickListener = object : AdapterClickListener<ThumbnailDto> {
                override fun onClick(view: View, model: ThumbnailDto, position: Int) {
                    when (model.type) {
                        1 -> {
                           viewModel.selectThumbnail(model.id) // Select
                        }
                        2, 3 -> {
                            showBottomFragment(thumbnailDto = model) // Save - Update
                        }
                    }
                }
            }
            tagsAdapter.adapterClickListener = object : AdapterClickListener<TagDto> {
                override fun onClick(view: View, model: TagDto, position: Int) {
                    if (model.type == 3) {
                        showBottomFragment(tagDto = model)
                    } else if (view.id == R.id.remove_btn) {
                        viewModel.deleteTag(model)
                    }
                }
            }
        }
        viewModel.itemFieldsContent.observe(viewLifecycleOwner) { item ->
            val tags = item.tagsAsList
                .map { TagDto(it, 1) }
                .toMutableList()
            tags.add(TagDto())
            tagsAdapter.dataSet = tags
        }
        viewModel.thumbnails.asLiveData().observe(viewLifecycleOwner) {
            thumbnailsAdapter.submitList(it)
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

/*    private fun applyMockData() {
        tagsAdapter.dataSet = MockData.tagsWithAdd
        thumbnailsAdapter.dataSet = MockData.thumbnailsWithAdds
        fieldsAdapter.dataSet = MockData.fieldsContent
        binding?.notesField?.setText(MockData.items[0].notes)
    }*/

}