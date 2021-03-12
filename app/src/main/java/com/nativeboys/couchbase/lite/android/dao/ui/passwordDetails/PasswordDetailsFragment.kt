package com.nativeboys.couchbase.lite.android.dao.ui.passwordDetails

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.nativeboys.couchbase.lite.android.dao.R
import com.nativeboys.couchbase.lite.android.dao.data.MockData
import com.nativeboys.couchbase.lite.android.dao.databinding.FragmentPasswordDetailsBinding
import com.nativeboys.couchbase.lite.android.dao.ui.adapters.tags.TagsAdapter
import com.nativeboys.couchbase.lite.android.dao.ui.adapters.thumbnails.ThumbnailsAdapter
import java.util.*

class PasswordDetailsFragment : Fragment(R.layout.fragment_password_details), View.OnClickListener {

    private var binding: FragmentPasswordDetailsBinding? = null
    private val tagsAdapter = TagsAdapter()
    private val thumbnailsAdapter = ThumbnailsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordDetailsBinding.bind(view)

        val layoutManager = FlexboxLayoutManager(view.context)
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.alignItems = AlignItems.FLEX_START

        binding?.tagsRecyclerView?.layoutManager = layoutManager
        binding?.tagsRecyclerView?.adapter = tagsAdapter

        binding?.thumbnailsRecyclerView?.layoutManager = LinearLayoutManager(
            view.context,
            RecyclerView.HORIZONTAL,
            false
        )
        binding?.thumbnailsRecyclerView?.adapter = thumbnailsAdapter

        binding?.dismissBtn?.setOnClickListener(this)
        binding?.clearWebsiteBtn?.setOnClickListener(this)
        binding?.clearEmailBtn?.setOnClickListener(this)
        binding?.clearPasswordBtn?.setOnClickListener(this)
        binding?.generatePasswordBtn?.setOnClickListener(this)

        applyMockData()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.clear_website_btn -> {
                binding?.websiteField?.text = null
            }
            R.id.clear_email_btn -> {
                binding?.emailField?.text = null
            }
            R.id.clear_password_btn -> {
                binding?.passwordField?.let {
                    val hidden = it.transformationMethod is PasswordTransformationMethod
                    val updatedMethod: TransformationMethod = if (hidden) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
                    it.transformationMethod = updatedMethod
                    it.setSelection(it.length())
                }
            }
            R.id.generate_password_btn -> {
                binding?.passwordField?.setText(UUID.randomUUID().toString())
            }
            R.id.dismiss_btn -> {
                activity?.onBackPressed()
            }
        }
    }

    private fun applyMockData() {
        tagsAdapter.dataSet = MockData.adapterTagsWithAdd
        thumbnailsAdapter.dataSet = MockData.adapterThumbnailsWithAdd
    }

}