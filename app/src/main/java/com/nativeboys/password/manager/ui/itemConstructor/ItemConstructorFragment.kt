package com.nativeboys.password.manager.ui.itemConstructor

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ThumbnailModel
import com.nativeboys.password.manager.data.MockData
import com.nativeboys.password.manager.data.TagModel
import com.nativeboys.password.manager.data.wrapCells
import com.nativeboys.password.manager.databinding.FragmentItemConstructorBinding
import com.nativeboys.password.manager.ui.adapters.tags.TagsAdapter
import com.nativeboys.password.manager.ui.adapters.thumbnails.ThumbnailsAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import java.util.*

class ItemConstructorFragment : Fragment(R.layout.fragment_item_constructor), View.OnClickListener {

    private var binding: FragmentItemConstructorBinding? = null
    private val tagsAdapter = TagsAdapter()
    private val thumbnailsAdapter = ThumbnailsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentItemConstructorBinding.bind(view)

        val layoutManager = FlexboxLayoutManager(view.context)
        layoutManager.wrapCells()

        binding?.let {
            it.headerContainer.headlineField.setText(R.string.create_item)
            it.tagsRecyclerView.layoutManager = layoutManager
            it.tagsRecyclerView.adapter = tagsAdapter
            it.thumbnailsRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
            it.thumbnailsRecyclerView.adapter = thumbnailsAdapter
        }

        setUpListeners()
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
            R.id.leading_btn -> {
                activity?.onBackPressed()
            }
        }
    }

    private fun setUpListeners() {
        val binding = binding ?: return

        binding.headerContainer.leadingBtn.setOnClickListener(this)
        binding.headerContainer.trailignBtn.setOnClickListener(this)
        binding.clearWebsiteBtn.setOnClickListener(this)
        binding.clearEmailBtn.setOnClickListener(this)
        binding.clearPasswordBtn.setOnClickListener(this)
        binding.generatePasswordBtn.setOnClickListener(this)

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
                } else if (view.id == R.id.remove_btn){
                    Log.i("Remove", model.name)
                }
            }
        }

    }

    private fun applyMockData() {
        tagsAdapter.dataSet = MockData.tagsWithAdd
        thumbnailsAdapter.dataSet = MockData.thumbnailsWithAdds
    }

}