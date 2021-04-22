package com.nativeboys.password.manager.ui.adapters.categoriesIcons

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.UICategoryIcon
import com.zeustech.zeuskit.ui.rv.RecyclerListAdapter

class CategoriesIconsAdapter : RecyclerListAdapter<UICategoryIcon, CategoriesIconsViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.category_icon_cell

    override fun getViewHolder(view: View, viewType: Int) = CategoriesIconsViewHolder(view)

}