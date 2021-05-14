package com.nativeboys.password.manager.ui.adapters.categoriesSelection

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryDto
import com.nativeboys.uikit.rv.RecyclerListAdapter

class CategoriesSelectionAdapter : RecyclerListAdapter<CategoryDto, CategoriesSelectionViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.description_cell

    override fun getViewHolder(view: View, viewType: Int) = CategoriesSelectionViewHolder(view)

}