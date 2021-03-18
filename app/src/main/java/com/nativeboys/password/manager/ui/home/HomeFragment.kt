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
import com.nativeboys.password.manager.data.MockData
import com.nativeboys.password.manager.data.ItemModel
import com.nativeboys.password.manager.databinding.FragmentHomeBinding
import com.nativeboys.password.manager.ui.adapters.items.ItemsAdapter
import com.nativeboys.password.manager.ui.adapters.tags.TagsAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener

class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {

    private lateinit var navController: NavController
    private val tagsAdapter = TagsAdapter()
    private val passwordsAdapter = ItemsAdapter()
    private var binding: FragmentHomeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        navController = Navigation.findNavController(view)

        val b = binding ?: return
        b.passwordsContainer.tagsRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        b.passwordsContainer.tagsRecyclerView.adapter = tagsAdapter
        b.passwordsContainer.passwordsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        b.passwordsContainer.passwordsRecyclerView.adapter = passwordsAdapter

        b.passwordsContainer.searchBtn.setOnClickListener(this)
        b.passwordsContainer.settingsBtn.setOnClickListener(this)
        b.passwordsContainer.plusBtn.setOnClickListener(this)
        passwordsAdapter.adapterClickListener = object : AdapterClickListener<ItemModel> {
            override fun onClick(view: View, model: ItemModel, position: Int) {
                navController.navigate(R.id.action_home_to_passwordDetails)
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
        tagsAdapter.dataSet = MockData.adapterTags
        passwordsAdapter.dataSet = MockData.items
    }

}