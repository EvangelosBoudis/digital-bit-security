package com.nativeboys.password.manager.ui.itemConstructor.bottomFragment

import android.os.Bundle
import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.TagDto
import com.nativeboys.password.manager.databinding.FragmentFactoryBottomBinding

class TagFactoryBottomFragment : FactoryBottomFragment() {

    private var tag: TagDto? = null
    private val tagName: String
        get() {
            return tag?.name ?: ""
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            tag = bundle.getParcelable(TAG_DATA)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFactoryBottomBinding.bind(view)
        binding.apply {
            fieldHeadline.setText(R.string.name)
            headerHeadline.setText(R.string.edit_tag)
            field.setText(tagName)
            clearFieldBtn.setOnClickListener {
                field.text = null
            }
            deleteBtn.visibility = if (tagName.isEmpty()) View.INVISIBLE else View.VISIBLE
            deleteBtn.setOnClickListener {
                if (tagName.isNotEmpty()) {
                    viewModel.deleteTag(tagName)
                    dismiss()
                }
            }
            submitBtn.setOnClickListener {
                val textContent = field.text.toString()
                if (textContent.isNotEmpty()) {
                    if (tagName.isEmpty()) viewModel.addTag(textContent)
                    else viewModel.updateTagName(tagName, textContent)
                    dismiss()
                }
            }
        }
    }

    companion object {

        private const val TAG_DATA = "tagData"

        @JvmStatic
        fun newInstance(tag: TagDto) =
            TagFactoryBottomFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TAG_DATA, tag)
                }
            }

    }

}