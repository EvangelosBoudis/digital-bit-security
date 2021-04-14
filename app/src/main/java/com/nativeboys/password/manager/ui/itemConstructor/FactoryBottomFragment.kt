package com.nativeboys.password.manager.ui.itemConstructor

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.TagDto
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.password.manager.databinding.FragmentFactoryBottomBinding
import com.zeustech.zeuskit.ui.fragments.BottomFragment
import com.zeustech.zeuskit.ui.views.BottomBar

class FactoryBottomFragment : BottomFragment(R.layout.fragment_factory_bottom) {

    private val viewModel: ItemConstructorViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private var tag: TagDto? = null
    private var thumbnail: ThumbnailDto? = null
    private var type: Int = 1
    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tag = it.getParcelable(TAG_DATA)
            thumbnail = it.getParcelable(THUMBNAIL_DATA)
        }
        type = if (thumbnail != null) 1 else 2
        id = thumbnail?.id ?: tag?.name ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFactoryBottomBinding.bind(view)
        binding.apply {
            fieldHeadline.setText(if (type == 1) R.string.url else R.string.name)
            headerHeadline.setText(if (type == 1) R.string.edit_thumbnail else R.string.edit_tag)
            field.setText(thumbnail?.url ?: tag?.name)
            clearFieldBtn.setOnClickListener {
                field.text = null
            }
            deleteBtn.visibility = if ((thumbnail?.url ?: tag?.name)?.isEmpty() != false) View.INVISIBLE else View.VISIBLE
            deleteBtn.setOnClickListener {
                deleteItem()
            }
            submitBtn.setOnClickListener {
                submitItem(field.text.toString())
            }
        }
    }

    private fun submitItem(textContent: String) {
        if (id.isEmpty()) { // Save
            if (type == 1) { // Thumbnail
                viewModel.addAndSelectThumbnail(textContent)
            } else { // Tag
                viewModel.addTag(textContent)
            }
        } else { // Update
            if (type == 1) { // Thumbnail
                thumbnail?.let {
                    viewModel.updateThumbnailUrlAndSelect(it.id, textContent)
                }
            } else { // Tag
                tag?.let {
                    viewModel.updateTagName(it.name, textContent)
                }
            }
        }
        dismiss()
    }

    private fun deleteItem() {
        if (id.isEmpty()) return
        if (type == 1) {
            viewModel.deleteThumbnail(id).observe(viewLifecycleOwner) { success ->
                if (success) {
                    dismiss()
                } else {
                    // TODO: ShowBottom
                }
            }
        }
        else {
            viewModel.deleteTag(id)
            dismiss()
        }
    }

    companion object {

        private const val TAG_DATA = "tagData"
        private const val THUMBNAIL_DATA = "thumbnailData"

        @JvmStatic
        fun newInstance(tag: TagDto?, thumbnail: ThumbnailDto?) =
            FactoryBottomFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TAG_DATA, tag)
                    putParcelable(THUMBNAIL_DATA, thumbnail)
                }
            }

    }

}