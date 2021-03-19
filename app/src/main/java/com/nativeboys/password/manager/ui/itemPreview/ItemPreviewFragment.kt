package com.nativeboys.password.manager.ui.itemPreview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ContentFieldModel
import com.nativeboys.password.manager.data.MockData
import com.nativeboys.password.manager.databinding.FragmentItemPreviewBinding
import com.nativeboys.password.manager.ui.adapters.fields.FieldsAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener

class ItemPreviewFragment :
    Fragment(R.layout.fragment_item_preview),
    AdapterClickListener<ContentFieldModel>,
    View.OnClickListener
{

    private val fieldsAdapter = FieldsAdapter()
    private var binding: FragmentItemPreviewBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentItemPreviewBinding.bind(view)
        binding?.let {
            it.fieldsRecyclerView.layoutManager = LinearLayoutManager(view.context)
            it.fieldsRecyclerView.adapter = fieldsAdapter
            it.leadingBtn.setOnClickListener(this)
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

        binding.headlineField.text = item.webSite

        fieldsAdapter.dataSet = MockData.contentFields
    }

    override fun onClick(view: View, model: ContentFieldModel, position: Int) {
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