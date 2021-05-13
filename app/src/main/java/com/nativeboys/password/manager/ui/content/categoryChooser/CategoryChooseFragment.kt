package com.nativeboys.password.manager.ui.content.categoryChooser

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
import com.nativeboys.password.manager.presentation.CategoryChooseViewModel
import com.nativeboys.password.manager.ui.adapters.categoriesChooser.CategoriesChooserAdapter
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryChooseFragment : ZTransactionFragment(R.layout.fragment_category_choose), AdapterClickListener<CategoryData> {

    private val viewModel: CategoryChooseViewModel by viewModels()

    private lateinit var navController: NavController
    private val categoriesAdapter = CategoriesChooserAdapter()

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
        navController.navigate(CategoryChooseFragmentDirections.actionCategoryChooseToItemConstructor(null, model.id))
    }

}