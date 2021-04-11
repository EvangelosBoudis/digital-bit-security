package com.nativeboys.password.manager.ui.home.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryDto
import com.nativeboys.password.manager.data.ItemDto
import com.nativeboys.password.manager.databinding.FragmentItemsBinding
import com.nativeboys.password.manager.other.parentNavController
import com.nativeboys.password.manager.ui.adapters.categoriesDto.CategoriesDtoAdapter
import com.nativeboys.password.manager.ui.adapters.itemsDto.ItemsDtoAdapter
import com.nativeboys.password.manager.ui.home.HomeFragmentDirections
import com.nativeboys.password.manager.ui.itemConstructor.FactoryBottomFragment
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsFragment : Fragment(R.layout.fragment_items), View.OnClickListener {

    private val viewModel: ItemsViewModel by viewModels()

    private lateinit var navController: NavController
    private val categoriesAdapter = CategoriesDtoAdapter()
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
        viewModel.categories.observe(viewLifecycleOwner) {
            categoriesAdapter.submitList(it)
        }
        viewModel.itemsDto.observe(viewLifecycleOwner) { items ->
            itemsAdapter.submitList(items)
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
                        navigateToItemOverview(model.itemId)
                    }
                    R.id.edit_btn -> {
                        parentNavController()?.navigate(R.id.action_homeFragment_to_itemConstructorFragment)
                    }
                    R.id.delete_btn -> {
                        viewModel.deleteItem(model.itemId)
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.settings_btn -> {
                SettingsBottomFragment().show(
                    this.childFragmentManager,
                    FactoryBottomFragment::class.java.simpleName
                )
            }
            R.id.plus_btn -> {
                parentNavController()?.navigate(R.id.action_homeFragment_to_itemConstructorFragment)
            }
        }
    }

    private fun navigateToItemOverview(itemId: String) {
        parentNavController()?.apply {
            val action = HomeFragmentDirections.actionHomeFragmentToItemOverviewFragment(itemId)
            navigate(action)
        }
    }

}