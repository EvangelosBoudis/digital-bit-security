package com.nativeboys.password.manager.ui.itemOverview

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.snackbar.Snackbar
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldContentModel
import com.nativeboys.password.manager.data.MockData
import com.nativeboys.password.manager.databinding.FragmentItemOverviewBinding
import com.nativeboys.password.manager.ui.adapters.fields.FieldsAdapter
import com.nativeboys.password.manager.ui.adapters.tags.TagsAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import com.zeustech.zeuskit.ui.views.BottomBar

class ItemOverviewFragment :
    Fragment(R.layout.fragment_item_overview),
    AdapterClickListener<FieldContentModel>,
    View.OnClickListener
{
    private lateinit var navController: NavController
    private var binding: FragmentItemOverviewBinding? = null

    private lateinit var fieldsAdapter: FieldsAdapter
    private lateinit var tagsAdapter: TagsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentItemOverviewBinding.bind(view)
        fieldsAdapter = FieldsAdapter()
        tagsAdapter = TagsAdapter()
        binding?.let {
            setUpView(it)
            setUpListeners(it)
        }
        applyMockData()
    }

    private fun setUpView(binding: FragmentItemOverviewBinding) {
        binding.headerContainer.headlineField.setText(R.string.item_overview)
        binding.headerContainer.trailignBtn.visibility = View.INVISIBLE
        binding.fieldsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.fieldsRecyclerView.adapter = fieldsAdapter
    }

    private fun setUpListeners(binding: FragmentItemOverviewBinding) {
        binding.headerContainer.leadingBtn.setOnClickListener(this)
        binding.editBtn.setOnClickListener(this)
        binding.deleteBtn.setOnClickListener(this)
        fieldsAdapter.adapterClickListener = this
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

        binding.nameField.text = item.name
        binding.descriptionField.text = item.description
        binding.notesField.setText(item.notes)
        fieldsAdapter.dataSet = MockData.fieldsContent
        binding.tagsField.setText(MockData.tags.joinToString(", ") { it.name })
    }

    override fun onClick(view: View, model: FieldContentModel, position: Int) {
        if (view.id == R.id.copy_btn) {
            val clipboard = getSystemService(requireContext(), ClipboardManager::class.java)
            val clip = ClipData.newPlainText("password:manager:field", model.content)
            clipboard?.setPrimaryClip(clip)
            showSnackBar(R.layout.copy_bottom_cell)
        }
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.leading_btn -> {
                activity?.onBackPressed()
            }
            R.id.edit_btn -> {
                navController.navigate(R.id.action_itemOverview_to_itemConstructor)
            }
            R.id.delete_btn -> {
                // TODO: implement
            }
        }
    }

    private fun showSnackBar(@LayoutRes layout: Int) {
        BottomBar(
            requireView() as ViewGroup,
            layout,
            Snackbar.LENGTH_SHORT
        ).show()
    }

}