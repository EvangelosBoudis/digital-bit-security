package com.nativeboys.password.manager.ui.adapters.filters

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.other.CategoryDto
import com.zeustech.zeuskit.ui.rv.RecyclerAdapter

class FiltersAdapter : RecyclerAdapter<CategoryDto, FiltersViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.descriptions_cell

    override fun getViewHolder(view: View, viewType: Int) = FiltersViewHolder(view)

}