package com.nativeboys.password.manager.ui.itemConstructor.bottomFragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.password.manager.databinding.FragmentFactoryBottomBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ThumbnailFactoryBottomFragment : FactoryBottomFragment() {

    private var thumbnail: ThumbnailDto? = null
    private val thumbnailId: String
        get() {
            return thumbnail?.id ?: ""
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            thumbnail = bundle.getParcelable(THUMBNAIL_DATA)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFactoryBottomBinding.bind(view)
        binding.apply {
            fieldHeadline.setText(R.string.url)
            headerHeadline.setText(R.string.edit_thumbnail)
            field.setText(thumbnail?.url)
            clearFieldBtn.setOnClickListener {
                field.text = null
            }
            deleteBtn.visibility = if (thumbnailId.isEmpty()) View.INVISIBLE else View.VISIBLE
            deleteBtn.setOnClickListener {
                thumbnail?.let {
                    viewModel.deleteThumbnail(it).observe(viewLifecycleOwner) { success ->
                        if (success) {
                            dismiss()
                        } else {
                            warningField.visibility = View.VISIBLE
                            lifecycleScope.launch {
                                delay(2000) // Delays coroutine for a given time without blocking a thread and resumes it after a specified time.
                                warningField.visibility = View.INVISIBLE
                            }
                        }
                    }
                }
            }
            submitBtn.setOnClickListener {
                val textContent = field.text.toString()
                if (textContent.isNotEmpty()) {
                    if (thumbnailId.isEmpty()) {
                        viewModel.addAndSelectThumbnail(textContent)
                    } else {
                        thumbnail?.let { viewModel.updateThumbnailUrlAndSelect(it, textContent) }
                    }
                    dismiss()
                }
            }
        }
    }

    companion object {

        private const val THUMBNAIL_DATA = "thumbnailData"

        @JvmStatic
        fun newInstance(thumbnail: ThumbnailDto) =
            ThumbnailFactoryBottomFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(THUMBNAIL_DATA, thumbnail)
                }
            }

    }

}