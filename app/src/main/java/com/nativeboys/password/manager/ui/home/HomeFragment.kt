package com.nativeboys.password.manager.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ItemDto
import com.nativeboys.password.manager.databinding.FragmentHomeBinding
import com.nativeboys.password.manager.other.CategoryDto
import com.nativeboys.password.manager.ui.adapters.filters.FiltersAdapter
import com.nativeboys.password.manager.ui.adapters.items.ItemsAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var navController: NavController
    private var binding: FragmentHomeBinding? = null

    private val filtersAdapter = FiltersAdapter()
    private val itemsAdapter = ItemsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        navController = Navigation.findNavController(view)
        binding?.apply {
            itemsContainer.filtersRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
            itemsContainer.filtersRecyclerView.adapter = filtersAdapter
            itemsContainer.itemsRecyclerView.layoutManager = LinearLayoutManager(view.context)
            itemsContainer.itemsRecyclerView.adapter = itemsAdapter
            itemsContainer.searchBtn.setOnClickListener(this@HomeFragment)
            itemsContainer.settingsBtn.setOnClickListener(this@HomeFragment)
            itemsContainer.plusBtn.setOnClickListener(this@HomeFragment)
        }
        itemsAdapter.adapterClickListener = object : AdapterClickListener<ItemDto> {
            override fun onClick(view: View, model: ItemDto, position: Int) {
                when (view.id) {
                    R.id.visible_view -> {
                        navController.navigate(R.id.action_home_to_itemPreview)
                    }
                    R.id.edit_btn -> {
                        navController.navigate(R.id.action_home_to_itemConstructor)
                    }
                    R.id.delete_btn -> {
                        viewModel.deleteItem(model.itemId)
                    }
                }
            }
        }
        filtersAdapter.adapterClickListener = object : AdapterClickListener<CategoryDto> {
            override fun onClick(view: View, model: CategoryDto, position: Int) {
                viewModel.setSelectedCategory(model.id)
            }
        }
        viewModel.categories.observe(viewLifecycleOwner) {
            filtersAdapter.dataSet = it
        }
        viewModel.itemsDto.observe(viewLifecycleOwner) { items ->
            itemsAdapter.dataSet = items
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.search_btn -> {
                navController.navigate(R.id.action_home_to_searchEngine)
            }
            R.id.settings_btn -> {
                binding?.drawerLayout?.openDrawer(GravityCompat.START)
            }
            R.id.plus_btn -> {
                navController.navigate(R.id.action_home_to_categories)
            }
        }
    }

}