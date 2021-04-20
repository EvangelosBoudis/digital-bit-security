package com.nativeboys.password.manager.ui.categoryChooser

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.databinding.FragmentCategoryChooseBinding
import com.nativeboys.password.manager.util.ZTransactionFragment
import com.nativeboys.password.manager.presentation.CategoriesViewModel
import com.nativeboys.password.manager.ui.adapters.categories.CategoriesAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryChooseFragment : ZTransactionFragment(R.layout.fragment_category_choose), AdapterClickListener<CategoryData> {

    private val viewModel: CategoriesViewModel by viewModels()

    private lateinit var navController: NavController
    private val categoriesAdapter = CategoriesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val binding = FragmentCategoryChooseBinding.bind(view)
        binding.apply {
            headerContainer.headlineField.setText(R.string.choose_category)
            headerContainer.trailingBtn.visibility = View.INVISIBLE
            categoriesRecyclerView.layoutManager = LinearLayoutManager(view.context)
            categoriesRecyclerView.adapter = categoriesAdapter
            headerContainer.leadingBtn.setOnClickListener {
                activity?.onBackPressed()
            }
        }
        categoriesAdapter.adapterClickListener = this
        viewModel.categories.observe(viewLifecycleOwner) {
            categoriesAdapter.submitList(it)
        }
    }

    override fun onClick(view: View, model: CategoryData, position: Int) {
        val action = CategoryChooseFragmentDirections.actionCategoryChooseFragmentToItemConstructorFragment(null, model.id)
        navController.navigate(action)
    }

}