package com.nativeboys.password.manager.ui.content.home.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.data.Result
import com.nativeboys.password.manager.databinding.FragmentCategoriesBinding
import com.nativeboys.password.manager.presentation.CategoriesViewModel
import com.nativeboys.password.manager.ui.adapters.categories.CategoriesAdapter
import com.nativeboys.password.manager.ui.content.confirmation.ConfirmationFragment
import com.nativeboys.password.manager.ui.content.home.HomeFragmentDirections
import com.nativeboys.password.manager.util.FragmentDialogListener
import com.nativeboys.password.manager.util.parentNavController
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment(
    R.layout.fragment_categories
), AdapterClickListener<CategoryData>, View.OnClickListener, FragmentDialogListener {

    private val viewModel: CategoriesViewModel by viewModels()

    private lateinit var navController: NavController
    private val categoriesAdapter = CategoriesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCategoriesBinding.bind(view)
        navController = Navigation.findNavController(view)
        binding.apply {
            categoriesRecyclerView.layoutManager = LinearLayoutManager(view.context)
            categoriesRecyclerView.adapter = categoriesAdapter
            plusBtn.setOnClickListener(this@CategoriesFragment)
        }
        childFragmentManager.addFragmentOnAttachListener { _, fragment ->
            (fragment as? ConfirmationFragment)?.let {
                it.fragmentDialogListener = this
            }
        }
        categoriesAdapter.adapterClickListener = this
        viewModel.categories.observe(viewLifecycleOwner) {
            categoriesAdapter.submitList(it)
        }
    }

    override fun onClick(view: View, model: CategoryData, position: Int) {
        if (model.defaultCategory) return
        when (view.id) {
            R.id.visible_view -> {
                moveToCategoryFragment(model.id)
            }
            R.id.delete_btn -> {
                viewModel.setPendingCategoryToDelete(model.id)
                ConfirmationFragment
                    .newInstance(R.layout.dialog_confirmation, getString(R.string.remove_category_confirmation), getString(R.string.remove_category_description))
                    .show(childFragmentManager, ConfirmationFragment::class.java.simpleName)
            }
        }
    }

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
        parentNavController()?.apply {
            navigate(HomeFragmentDirections.actionHomeToCategoryConstructor(id))
        }
    }

    override fun onClick(dialogFragment: DialogFragment, view: View) {
        when (view.id) {
            R.id.leading_btn -> {
                dialogFragment.dismiss()
            }
            R.id.trailing_btn -> {
                viewModel.deleteCategory().observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Success -> {
                            dialogFragment.dismiss()
                        }
                    }
                }
            }
        }
    }

}