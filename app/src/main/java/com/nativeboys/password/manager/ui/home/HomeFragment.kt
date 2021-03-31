package com.nativeboys.password.manager.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ItemData
import com.nativeboys.password.manager.other.MockData
import com.nativeboys.password.manager.databinding.FragmentHomeBinding
import com.nativeboys.password.manager.ui.adapters.filters.FiltersAdapter
import com.nativeboys.password.manager.ui.adapters.items.ItemsAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener

class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {

    private lateinit var navController: NavController
    private val filtersAdapter = FiltersAdapter()
    private val passwordsAdapter = ItemsAdapter()
    private var binding: FragmentHomeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        navController = Navigation.findNavController(view)
        binding?.apply {
            passwordsContainer.filtersRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
            passwordsContainer.filtersRecyclerView.adapter = filtersAdapter
            passwordsContainer.passwordsRecyclerView.layoutManager = LinearLayoutManager(view.context)
            passwordsContainer.passwordsRecyclerView.adapter = passwordsAdapter
            passwordsContainer.searchBtn.setOnClickListener(this@HomeFragment)
            passwordsContainer.settingsBtn.setOnClickListener(this@HomeFragment)
            passwordsContainer.plusBtn.setOnClickListener(this@HomeFragment)
        }
        passwordsAdapter.adapterClickListener = object : AdapterClickListener<ItemData> {
            override fun onClick(view: View, model: ItemData, position: Int) {
                when (view.id) {
                    R.id.visible_view -> {
                        navController.navigate(R.id.action_home_to_itemPreview)
                    }
                    R.id.edit_btn -> {
                        navController.navigate(R.id.action_home_to_itemConstructor)
                    }
                    R.id.delete_btn -> {
                        // TODO: implement
                    }
                }
            }
        }
        applyMockData()
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

    private fun applyMockData() {
        filtersAdapter.dataSet = MockData.filters
        passwordsAdapter.dataSet = MockData.items
    }

}