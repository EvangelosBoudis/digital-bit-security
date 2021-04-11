package com.nativeboys.password.manager.ui.itemConstructor

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.other.ThumbnailModel
import com.nativeboys.password.manager.other.MockData
import com.nativeboys.password.manager.other.TagModel
import com.nativeboys.password.manager.other.wrapCells
import com.nativeboys.password.manager.databinding.FragmentItemConstructorBinding
import com.nativeboys.password.manager.ui.adapters.fieldContent.FieldContentAdapter
import com.nativeboys.password.manager.ui.adapters.tags.TagsAdapter
import com.nativeboys.password.manager.ui.adapters.thumbnails.ThumbnailsAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import java.util.*

class ItemConstructorFragment : Fragment(R.layout.fragment_item_constructor), View.OnClickListener {

    private var binding: FragmentItemConstructorBinding? = null

    private lateinit var fieldsAdapter: FieldContentAdapter
    private lateinit var tagsAdapter: TagsAdapter
    private lateinit var thumbnailsAdapter: ThumbnailsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentItemConstructorBinding.bind(view)
        fieldsAdapter = FieldContentAdapter()
        thumbnailsAdapter = ThumbnailsAdapter()
        tagsAdapter = TagsAdapter()
        binding?.let {
            setUpView(it)
            setUpListeners(it)
        }
        applyMockData()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
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

    private fun setUpView(binding: FragmentItemConstructorBinding) {
        binding.fieldsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.fieldsRecyclerView.adapter = fieldsAdapter
        binding.headerContainer.headlineField.setText(R.string.create_item)
        binding.thumbnailsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.thumbnailsRecyclerView.adapter = thumbnailsAdapter
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.wrapCells()
        binding.tagsRecyclerView.layoutManager = layoutManager
        binding.tagsRecyclerView.adapter = tagsAdapter
    }

    private fun setUpListeners(binding: FragmentItemConstructorBinding) {
        binding.headerContainer.leadingBtn.setOnClickListener(this)
        binding.headerContainer.trailingBtn.setOnClickListener(this)
        thumbnailsAdapter.adapterClickListener = object : AdapterClickListener<ThumbnailModel> {
            override fun onClick(view: View, model: ThumbnailModel, position: Int) {
                if (model.type == 3) {
                    FactoryBottomFragment.showFragment(
                        this@ItemConstructorFragment,
                        R.string.add_thumbnail,
                        R.string.url
                    )
                }
            }
        }
        tagsAdapter.adapterClickListener = object : AdapterClickListener<TagModel> {
            override fun onClick(view: View, model: TagModel, position: Int) {
                if (model.type == 3) {
                    FactoryBottomFragment.showFragment(
                        this@ItemConstructorFragment,
                        R.string.add_tag,
                        R.string.name
                    )
                } else if (view.id == R.id.remove_btn) {
                    Log.i("Remove", model.name)
                }
            }
        }
    }

    private fun applyMockData() {
        tagsAdapter.dataSet = MockData.tagsWithAdd
        thumbnailsAdapter.dataSet = MockData.thumbnailsWithAdds
        fieldsAdapter.dataSet = MockData.fieldsContent
        binding?.notesField?.setText(MockData.items[0].notes)
    }

}