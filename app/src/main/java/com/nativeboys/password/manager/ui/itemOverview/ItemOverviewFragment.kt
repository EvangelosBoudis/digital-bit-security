package com.nativeboys.password.manager.ui.itemOverview

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
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
import com.nativeboys.password.manager.data.Result
import com.nativeboys.password.manager.databinding.FragmentItemOverviewBinding
import com.nativeboys.password.manager.util.ZTransactionFragment
import com.nativeboys.password.manager.ui.adapters.fields.FieldsAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import com.zeustech.zeuskit.ui.views.BottomBar
import dagger.hilt.android.AndroidEntryPoint
import com.nativeboys.password.manager.util.copyToClipboard
import com.nativeboys.password.manager.presentation.ItemOverviewViewModel
import com.nativeboys.password.manager.ui.confirmation.ConfirmationDialogListener
import com.nativeboys.password.manager.ui.confirmation.ConfirmationFragment

@AndroidEntryPoint
class ItemOverviewFragment : ZTransactionFragment(
    R.layout.fragment_item_overview
), View.OnClickListener, AdapterClickListener<FieldContentDto>, ConfirmationDialogListener {

    private val viewModel: ItemOverviewViewModel by viewModels()

    private lateinit var navController: NavController
    private var binding: FragmentItemOverviewBinding? = null
    private val fieldsAdapter = FieldsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentItemOverviewBinding.bind(view)
        binding?.apply {
            headerContainer.headlineField.setText(R.string.overview)
            headerContainer.trailingBtn.visibility = View.INVISIBLE
            fieldsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            fieldsRecyclerView.adapter = fieldsAdapter
            headerContainer.leadingBtn.setOnClickListener(this@ItemOverviewFragment)
            editBtn.setOnClickListener(this@ItemOverviewFragment)
            deleteBtn.setOnClickListener(this@ItemOverviewFragment)
            favoriteBtn.setOnClickListener(this@ItemOverviewFragment)
        }
        fieldsAdapter.adapterClickListener = this
        childFragmentManager.addFragmentOnAttachListener { _, fragment ->
            (fragment as? ConfirmationFragment)?.let {
                it.confirmationDialogListener = this
            }
        }

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
                tagsField.setText(item.formattedTags)
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
                val action = ItemOverviewFragmentDirections.actionItemOverviewFragmentToItemConstructorFragment(viewModel.itemId, null)
                navController.navigate(action)
            }
            R.id.delete_btn -> {
                ConfirmationFragment
                    .newInstance(R.layout.dialog_confirmation, getString(R.string.remove_item_confirmation), getString(R.string.remove_item_description))
                    .show(childFragmentManager, ConfirmationFragment::class.java.simpleName)
            }
        }
    }

    override fun onClick(view: View, model: FieldContentDto, position: Int) {
        if (view.id == R.id.copy_btn) {
            val textContent = model.textContent ?: return
            copyToClipboard(requireContext(), "password:manager:field", textContent)
            BottomBar(requireView() as ViewGroup, R.layout.copy_bottom_cell, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onClick(dialogFragment: DialogFragment, view: View) {
        when (view.id) {
            R.id.leading_btn -> {
                dialogFragment.dismiss()
            }
            R.id.trailing_btn -> {
                viewModel.deleteItem().observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Success -> {
                            dialogFragment.dismiss()
                            activity?.onBackPressed()
                        }
                    }
                }
            }
        }
    }

}