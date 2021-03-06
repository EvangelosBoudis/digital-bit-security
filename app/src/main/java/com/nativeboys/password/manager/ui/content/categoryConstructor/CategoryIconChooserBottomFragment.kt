package com.nativeboys.password.manager.ui.content.categoryConstructor

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.UICategoryIcon
import com.nativeboys.password.manager.databinding.FragmentCategoryIconChooserBottomBinding
import com.nativeboys.password.manager.presentation.CategoryConstructorViewModel
import com.nativeboys.password.manager.presentation.CategoryConstructorViewModel.Companion.THUMBNAIL_CODE
import com.nativeboys.password.manager.presentation.CategoryConstructorViewModel.Companion.THUMBNAIL_SEARCH_KEY
import com.nativeboys.password.manager.ui.adapters.categoriesIconSearch.CategoriesIconSearchAdapter
import com.nativeboys.password.manager.util.setTextAndFixCursor
import com.nativeboys.uikit.fragments.BottomFragment
import com.nativeboys.uikit.rv.AdapterClickListener
import com.nativeboys.uikit.other.KeyboardManager

class CategoryIconChooserBottomFragment : BottomFragment(
    R.layout.fragment_category_icon_chooser_bottom,
    85f,
    true
), AdapterClickListener<UICategoryIcon> {

    private val viewModel: CategoryConstructorViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private val categoriesIconsAdapter = CategoriesIconSearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCategoryIconChooserBottomBinding.bind(view)
        binding.apply {
            val portrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            recyclerView.layoutManager = GridLayoutManager(view.context, if (portrait) 5 else 8)
            recyclerView.adapter = categoriesIconsAdapter
            searchField.setTextAndFixCursor(viewModel.thumbnailSearchKey)
            searchField.addTextChangedListener {
                viewModel.updateUserCache(THUMBNAIL_SEARCH_KEY, it.toString())
            }
            searchField.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) KeyboardManager().hideKeyboard(v)
                return@setOnEditorActionListener true
            }
        }
        viewModel.observeCategoriesIcons.observe(viewLifecycleOwner) {
            categoriesIconsAdapter.submitList(it)
        }
        categoriesIconsAdapter.adapterClickListener = this
    }

    override fun onClick(view: View, model: UICategoryIcon, position: Int) {
        viewModel.updateUserCache(THUMBNAIL_CODE, model.thumbnailCode)
        dismiss()
    }

}