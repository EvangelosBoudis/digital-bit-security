package com.nativeboys.password.manager.ui.adapters.items

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ItemEntity
import com.zeustech.zeuskit.ui.rv.RecyclerAdapter
import com.zeustech.zeuskit.ui.swipeRevealLayout.ViewBinderHelper

class ItemsAdapter : RecyclerAdapter<ItemEntity, ItemsViewHolder>() {

    private val viewBinderHelper = ViewBinderHelper()

    init {
        viewBinderHelper.setOpenOnlyOne(true)
    }

    override fun getResId(viewType: Int) = R.layout.items_cell

    override fun getViewHolder(view: View, viewType: Int) = ItemsViewHolder(view, viewBinderHelper)

}