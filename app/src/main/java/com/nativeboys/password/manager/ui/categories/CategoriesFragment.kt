package com.nativeboys.password.manager.ui.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryEntity
import com.nativeboys.password.manager.databinding.FragmentCategoriesBinding
import com.nativeboys.password.manager.ui.adapters.categories.CategoriesAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment(R.layout.fragment_categories), AdapterClickListener<CategoryEntity>, View.OnClickListener {

    private val viewModel: CategoriesViewModel by viewModels()

    private lateinit var navController: NavController
    private val categoriesAdapter = CategoriesAdapter()
    private var binding: FragmentCategoriesBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoriesBinding.bind(view)
        navController = Navigation.findNavController(view)
        binding?.let {
            it.headerContainer.headlineField.setText(R.string.choose_category)
            it.headerContainer.trailignBtn.visibility = View.INVISIBLE
            it.categoriesRecyclerView.layoutManager = LinearLayoutManager(view.context)
            it.categoriesRecyclerView.adapter = categoriesAdapter
            it.headerContainer.leadingBtn.setOnClickListener(this)
            it.plusBtn.setOnClickListener(this)
        }
        categoriesAdapter.adapterClickListener = this

        viewModel.categories.observe(viewLifecycleOwner) {
            categoriesAdapter.dataSet = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onClick(view: View, model: CategoryEntity, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.plus_btn -> {
                navController.navigate(R.id.action_categories_to_categoryConstructor)
            }
            R.id.leading_btn -> {
                activity?.onBackPressed()
            }
        }
    }

}