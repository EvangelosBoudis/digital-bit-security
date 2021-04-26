package com.nativeboys.password.manager.ui.adapters.categoriesChooser

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.ui.adapters.SharedCategoriesViewHolder
import com.zeustech.zeuskit.ui.rv.RecyclerListAdapter

class CategoriesChooserAdapter : RecyclerListAdapter<CategoryData, SharedCategoriesViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.category_chooser_cell

    override fun getViewHolder(view: View, viewType: Int) = SharedCategoriesViewHolder(view)

}