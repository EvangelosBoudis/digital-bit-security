package com.nativeboys.password.manager.ui.adapters.categories

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.uikit.rv.RecyclerListAdapter
import com.nativeboys.uikit.swipeRevealLayout.ViewBinderHelper

class CategoriesAdapter : RecyclerListAdapter<CategoryData, CategoriesViewHolder>() {

    private val viewBinderHelper = ViewBinderHelper()

    init {
        viewBinderHelper.setOpenOnlyOne(true)
    }

    override fun getResId(viewType: Int) = R.layout.category_cell

    override fun getViewHolder(view: View, viewType: Int) = CategoriesViewHolder(view, viewBinderHelper)

}