package com.nativeboys.password.manager.ui.itemConstructor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.TagDto
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.password.manager.databinding.FragmentFactoryBottomBinding
import com.zeustech.zeuskit.ui.fragments.BottomFragment

class FactoryBottomFragment : BottomFragment(R.layout.fragment_factory_bottom) {

    private val viewModel: ItemConstructorViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private var tag: TagDto? = null
    private var thumbnail: ThumbnailDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tag = it.getParcelable(TAG_DATA)
            thumbnail = it.getParcelable(THUMBNAIL_DATA)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFactoryBottomBinding.bind(view)
        binding.apply {
            val type = if (thumbnail != null) 1 else 2
            fieldHeadline.setText(if (type == 1) R.string.url else R.string.name)
            headerHeadline.setText(if (type == 1) R.string.add_thumbnail else R.string.add_tag)
            field.setText(thumbnail?.url ?: tag?.name)
            clearFieldBtn.setOnClickListener {
                field.text = null
            }
            submitBtn.setOnClickListener {
                val content = field.text.toString()
                val id = thumbnail?.id ?: tag?.name
                id?.apply {
                    if (isEmpty()) { // Save
                        if (type == 1) { // Thumbnail
                            // "https://w7.pngwing.com/pngs/646/324/png-transparent-github-computer-icons-github-logo-monochrome-head-thumbnail.png"
                            viewModel.addAndSelectThumbnail(content)
                        } else { // Tag
                            viewModel.addTag(content)
                        }
                    } else { // Update
                        if (type == 1) { // Thumbnail
                            thumbnail?.let {
                                viewModel.updateAndSelectThumbnail(it, content)
                            }
                        } else { // Tag
                            tag?.let {
                                viewModel.updateTag(it, content)
                            }
                        }
                    }
                }
                dismiss()
            }
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