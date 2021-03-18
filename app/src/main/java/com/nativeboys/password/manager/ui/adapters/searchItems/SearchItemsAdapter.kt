package com.nativeboys.password.manager.ui.adapters.searchItems

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ItemModel
import com.zeustech.zeuskit.ui.rv.RecyclerAdapter

class SearchItemsAdapter : RecyclerAdapter<ItemModel, SearchItemsViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.search_items_cell

    override fun getViewHolder(view: View, viewType: Int) = SearchItemsViewHolder(view)

}