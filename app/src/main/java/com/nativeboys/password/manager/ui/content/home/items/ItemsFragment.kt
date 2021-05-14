package com.nativeboys.password.manager.ui.content.home.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.nativeboys.password.manager.ui.content.confirmation.ConfirmationFragment
import com.nativeboys.password.manager.ui.content.home.HomeFragmentDirections
import com.nativeboys.uikit.views.CenterLayoutManager
import com.nativeboys.uikit.fragments.FragmentDialogListener
import com.nativeboys.uikit.rv.AdapterClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemsFragment : Fragment(
    R.layout.fragment_items
), View.OnClickListener, FragmentDialogListener {

    private val viewModel: ItemsViewModel by viewModels()

    private lateinit var navController: NavController
    private val categoriesAdapter = CategoriesSelectionAdapter()
    private val itemsAdapter = ItemsDtoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentItemsBinding.bind(view)
        navController = Navigation.findNavController(view)
        binding.apply {
            categoriesRecyclerView.layoutManager = CenterLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
            categoriesRecyclerView.adapter = categoriesAdapter
            itemsRecyclerView.layoutManager = LinearLayoutManager(view.context)
            itemsRecyclerView.adapter = itemsAdapter
            settingsBtn.setOnClickListener(this@ItemsFragment)
            plusBtn.setOnClickListener(this@ItemsFragment)
        }
        childFragmentManager.addFragmentOnAttachListener { _, fragment ->
            (fragment as? ConfirmationFragment)?.let {
                it.fragmentDialogListener = this
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
                            if (model.requestPassword) navigate(HomeFragmentDirections.actionHomeToLogin(2, model.itemId))
                            else navigate(HomeFragmentDirections.actionHomeToItemOverview(model.itemId))
                        }
                    }
                    R.id.edit_btn -> {
                        parentNavController()?.apply {
                            if (model.requestPassword) navigate(HomeFragmentDirections.actionHomeToLogin(3, model.itemId))
                            else navigate(HomeFragmentDirections.actionHomeToItemConstructor(model.itemId, null))
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
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.submitList(categories)
            val position = categories.indexOfFirst { it.selected }
            lifecycleScope.launch {
                delay(200)
                if (position != -1) binding.categoriesRecyclerView.smoothScrollToPosition(position)
            }
        }
        viewModel.itemsDto.observe(viewLifecycleOwner) { items ->
            itemsAdapter.submitList(items)
        }
        viewModel.emptyData.observe(viewLifecycleOwner) { empty ->
            val visibility = if (empty) View.VISIBLE else View.GONE
            lifecycleScope.launch {
                delay(200)
                binding.emptyDataField.visibility = visibility
                binding.emptyDataHolder.visibility = visibility
            }
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
                parentNavController()?.navigate(R.id.action_home_to_categoryChoose)
            }
        }
    }

    override fun onClick(dialogFragment: DialogFragment, view: View) {
        if (view.id == R.id.trailing_btn) viewModel.deleteItem()
        dialogFragment.dismiss()
    }

}