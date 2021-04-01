package com.nativeboys.password.manager.ui.adapters.categoriesDto

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryDto
import com.zeustech.zeuskit.ui.rv.RecyclerListAdapter

class CategoriesDtoAdapter : RecyclerListAdapter<CategoryDto, CategoriesDtoViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.descriptions_cell

    override fun getViewHolder(view: View, viewType: Int) = CategoriesDtoViewHolder(view)

}