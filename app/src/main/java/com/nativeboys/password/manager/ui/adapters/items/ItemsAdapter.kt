package com.nativeboys.password.manager.ui.adapters.items

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ItemModel
import com.zeustech.zeuskit.ui.rv.RecyclerAdapter

class ItemsAdapter : RecyclerAdapter<ItemModel, ItemsViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.items_cell

    override fun getViewHolder(view: View, viewType: Int) = ItemsViewHolder(view)

}