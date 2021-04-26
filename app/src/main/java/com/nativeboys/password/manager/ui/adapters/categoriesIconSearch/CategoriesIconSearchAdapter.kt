package com.nativeboys.password.manager.ui.adapters.categoriesIconSearch

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.UICategoryIcon
import com.zeustech.zeuskit.ui.rv.RecyclerListAdapter

class CategoriesIconSearchAdapter : RecyclerListAdapter<UICategoryIcon, CategoriesIconSearchViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.category_icon_search_cell

    override fun getViewHolder(view: View, viewType: Int) = CategoriesIconSearchViewHolder(view)

}