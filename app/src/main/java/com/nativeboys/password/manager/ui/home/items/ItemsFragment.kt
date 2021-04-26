package com.nativeboys.password.manager.ui.home.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryDto
import com.nativeboys.password.manager.data.ItemDto
import com.nativeboys.password.manager.databinding.FragmentItemsBinding
import com.nativeboys.password.manager.util.parentNavController
import com.nativeboys.password.manager.presentation.ItemsViewModel
import com.nativeboys.password.manager.ui.adapters.categoriesSelection.CategoriesSelectionAdapter
import com.nativeboys.password.manager.ui.adapters.itemsDto.ItemsDtoAdapter
import com.nativeboys.password.manager.ui.confirmation.ConfirmationDialogListener
import com.nativeboys.password.manager.ui.confirmation.ConfirmationFragment
import com.nativeboys.password.manager.ui.home.HomeFragmentDirections
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsFragment : Fragment(
    R.layout.fragment_items
), View.OnClickListener, ConfirmationDialogListener {

    private val viewModel: ItemsViewModel by viewModels()

    private lateinit var navController: NavController
    private val categoriesAdapter = CategoriesSelectionAdapter()
    private val itemsAdapter = ItemsDtoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentItemsBinding.bind(view)
        navController = Navigation.findNavController(view)
        binding.apply {
            categoriesRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
            categoriesRecyclerView.adapter = categoriesAdapter
            itemsRecyclerView.layoutManager = LinearLayoutManager(view.context)
            itemsRecyclerView.adapter = itemsAdapter
            settingsBtn.setOnClickListener(this@ItemsFragment)
            plusBtn.setOnClickListener(this@ItemsFragment)
        }
        childFragmentManager.addFragmentOnAttachListener { _, fragment ->
            (fragment as? ConfirmationFragment)?.let {
                it.confirmationDialogListener = this
            }
        }
        categoriesAdapter.adapterClickListener = object : AdapterClickListener<CategoryDto> {
            override fun onClick(view: View, model: CategoryDto, position: Int) {
                viewModel.setSelectedCategory(model.id)
            }
        }
        itemsAdapter.adapterClickListener = object : AdapterClickListener<ItemDto> {
            override fun onClick(view: View, model: ItemDto, position: Int) {
                when (view.id) {
                    R.id.visible_view -> {
                        parentNavController()?.apply {
                            val action = HomeFragmentDirections.actionHomeFragmentToItemOverviewFragment(model.itemId)
                            navigate(action)
                        }
                    }
                    R.id.edit_btn -> {
                        parentNavController()?.apply {
                            val action = HomeFragmentDirections.actionHomeFragmentToItemConstructorFragment(model.itemId, null)
                            navigate(action)
                        }
                    }
                    R.id.delete_btn -> {
                        viewModel.setPendingItemToDelete(model.itemId)
                        ConfirmationFragment
                            .newInstance(R.layout.dialog_confirmation, getString(R.string.remove_item_confirmation), getString(R.string.remove_item_description))
                            .show(childFragmentManager, ConfirmationFragment::class.java.simpleName)
                    }
                }
            }
        }
        viewModel.categories.observe(viewLifecycleOwner) {
            categoriesAdapter.submitList(it)
        }
        viewModel.itemsDto.observe(viewLifecycleOwner) { items ->
            itemsAdapter.submitList(items)
        }
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.settings_btn -> {
                SettingsBottomFragment().show(
                    this.childFragmentManager,
                    SettingsBottomFragment::class.java.simpleName
                )
            }
            R.id.plus_btn -> {
                parentNavController()?.navigate(R.id.action_homeFragment_to_categoryChooseFragment)
            }
        }
    }

    override fun onClick(dialogFragment: DialogFragment, view: View) {
        if (view.id == R.id.trailing_btn) viewModel.deleteItem()
        dialogFragment.dismiss()
    }

}