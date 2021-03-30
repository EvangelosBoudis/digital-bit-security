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
        binding?.apply {
            headerContainer.headlineField.setText(R.string.choose_category)
            headerContainer.trailignBtn.visibility = View.INVISIBLE
            categoriesRecyclerView.layoutManager = LinearLayoutManager(view.context)
            categoriesRecyclerView.adapter = categoriesAdapter
            headerContainer.leadingBtn.setOnClickListener(this@CategoriesFragment)
            plusBtn.setOnClickListener(this@CategoriesFragment)
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

    override fun onClick(view: View, model: CategoryEntity, position: Int) = moveToCategoryFragment(model.id)

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.plus_btn -> {
                moveToCategoryFragment()
            }
            R.id.leading_btn -> {
                activity?.onBackPressed()
            }
        }
    }

    private fun moveToCategoryFragment(id: String? = null) {
        val action = CategoriesFragmentDirections.actionCategoriesToCategoryConstructor(id)
        navController.navigate(action)
    }

}