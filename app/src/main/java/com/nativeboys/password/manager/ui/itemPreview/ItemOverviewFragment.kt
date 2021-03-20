package com.nativeboys.password.manager.ui.itemPreview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldContentModel
import com.nativeboys.password.manager.data.MockData
import com.nativeboys.password.manager.databinding.FragmentItemOverviewBinding
import com.nativeboys.password.manager.ui.adapters.fields.FieldsAdapter
import com.nativeboys.password.manager.ui.adapters.tags.TagsAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener

class ItemOverviewFragment :
    Fragment(R.layout.fragment_item_overview),
    AdapterClickListener<FieldContentModel>,
    View.OnClickListener
{
    private lateinit var fieldsAdapter: FieldsAdapter
    private lateinit var tagsAdapter: TagsAdapter
    private var binding: FragmentItemOverviewBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentItemOverviewBinding.bind(view)
        fieldsAdapter = FieldsAdapter()
        tagsAdapter = TagsAdapter()

        binding?.let {
            it.headerContainer.headlineField.setText(R.string.item_overview)
            it.headerContainer.trailignBtn.visibility = View.INVISIBLE

            it.fieldsRecyclerView.layoutManager = LinearLayoutManager(view.context)
            it.fieldsRecyclerView.adapter = fieldsAdapter

            it.headerContainer.leadingBtn.setOnClickListener(this)
        }
        fieldsAdapter.adapterClickListener = this

        applyMockData()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun applyMockData() {
        val binding = binding ?: return
        val item = MockData.items[0]

        Glide.with(requireContext())
            .load(item.thumbnailUrl)
            .transform(CenterCrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.thumbnailHolder)

        binding.nameField.text = item.webSite
        binding.notesField.setText(item.notes)
        fieldsAdapter.dataSet = MockData.fieldsContent
        binding.tagsField.setText(MockData.tags.joinToString(", ") { it.name })
    }

    override fun onClick(view: View, model: FieldContentModel, position: Int) {
        if (view.id == R.id.copy_btn) {
            // TODO: 1. copy, 2. show snack bar
        }
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.leading_btn -> {
                activity?.onBackPressed()
            }
        }
    }

}