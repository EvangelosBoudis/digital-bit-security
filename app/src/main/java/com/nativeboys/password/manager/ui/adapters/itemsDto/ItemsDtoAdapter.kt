package com.nativeboys.password.manager.ui.adapters.itemsDto

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ItemDto
import com.zeustech.zeuskit.ui.rv.RecyclerListAdapter
import com.zeustech.zeuskit.ui.swipeRevealLayout.ViewBinderHelper

class ItemsDtoAdapter : RecyclerListAdapter<ItemDto, ItemsDtoViewHolder>() {

    private val viewBinderHelper = ViewBinderHelper()

    init {
        viewBinderHelper.setOpenOnlyOne(true)
    }

    override fun getResId(viewType: Int) = R.layout.items_cell

    override fun getViewHolder(view: View, viewType: Int) = ItemsDtoViewHolder(view, viewBinderHelper)

}