package com.nativeboys.password.manager.ui.itemOverview

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldContentDto
import com.nativeboys.password.manager.databinding.FragmentItemOverviewBinding
import com.nativeboys.password.manager.other.applyZTransition
import com.nativeboys.password.manager.ui.adapters.fields.FieldsAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import com.zeustech.zeuskit.ui.views.BottomBar
import dagger.hilt.android.AndroidEntryPoint
import com.nativeboys.password.manager.other.copyToClipboard
import com.nativeboys.password.manager.presentation.ItemOverviewViewModel

@AndroidEntryPoint
class ItemOverviewFragment : Fragment(R.layout.fragment_item_overview), AdapterClickListener<FieldContentDto>, View.OnClickListener {

    private val viewModel: ItemOverviewViewModel by viewModels()

    private lateinit var navController: NavController
    private var binding: FragmentItemOverviewBinding? = null

    private val fieldsAdapter = FieldsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyZTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentItemOverviewBinding.bind(view)
        binding?.apply {
            headerContainer.headlineField.setText(R.string.item_overview)
            headerContainer.trailingBtn.visibility = View.INVISIBLE
            fieldsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            fieldsRecyclerView.adapter = fieldsAdapter
            headerContainer.leadingBtn.setOnClickListener(this@ItemOverviewFragment)
            editBtn.setOnClickListener(this@ItemOverviewFragment)
            deleteBtn.setOnClickListener(this@ItemOverviewFragment)
            favoriteBtn.setOnClickListener(this@ItemOverviewFragment)
        }
        fieldsAdapter.adapterClickListener = this
        viewModel.itemFieldsContent.observe(viewLifecycleOwner) { itemFieldsContent ->
            val item = itemFieldsContent ?: return@observe
            binding?.apply {
                Glide.with(requireContext())
                    .load(item.thumbnailUrl)
                    .transform(CenterCrop())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(thumbnailHolder)
                updateFavoriteBtn(item.favorite)
                nameField.text = item.name
                descriptionField.text = item.description
                notesField.setText(item.notes)
                tagsField.setText(item.tags)
                fieldsAdapter.dataSet = item.fieldsContent
                val notesVisibility = if (item.notes?.isEmpty() == true) View.GONE else View.VISIBLE
                notesHeadline.visibility = notesVisibility
                notesField.visibility = notesVisibility
                val tagsVisibility = if (item.tags?.isEmpty() == true) View.GONE else View.VISIBLE
                tagsHeadline.visibility = tagsVisibility
                tagsField.visibility = tagsVisibility
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onClick(view: View, model: FieldContentDto, position: Int) {
        if (view.id == R.id.copy_btn) {
            copyToClipboard(requireContext(), "password:manager:field", model.textContent)
            BottomBar(requireView() as ViewGroup, R.layout.copy_bottom_cell, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun updateFavoriteBtn(favorite: Boolean) {
        binding?.apply {
            Glide.with(requireContext())
                .load(if (favorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24)
                .transition(DrawableTransitionOptions().crossFade())
                .into(favoriteBtn)
        }
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.favorite_btn -> {
                viewModel.toggleItemFavorite().observe(viewLifecycleOwner) {
                    updateFavoriteBtn(it)
                }
            }
            R.id.leading_btn -> {
                activity?.onBackPressed()
            }
            R.id.edit_btn -> {
                val action = ItemOverviewFragmentDirections.actionItemOverviewFragmentToItemConstructorFragment(viewModel.itemId)
                navController.navigate(action)
            }
            R.id.delete_btn -> {
                // TODO: show dialog
            }
        }
    }

}