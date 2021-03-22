package com.nativeboys.password.manager.ui.adapters.categories

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryData
import com.zeustech.zeuskit.ui.rv.RecyclerAdapter

class CategoriesAdapter : RecyclerAdapter<CategoryData, CategoriesViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.categories_cell

    override fun getViewHolder(view: View, viewType: Int) = CategoriesViewHolder(view)

}