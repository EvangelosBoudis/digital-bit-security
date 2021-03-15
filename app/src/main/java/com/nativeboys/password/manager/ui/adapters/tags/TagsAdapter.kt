package com.nativeboys.password.manager.ui.adapters.tags

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.AdapterTagModel
import com.nativeboys.password.manager.ui.adapters.AddViewHolder
import com.zeustech.zeuskit.ui.rv.RecyclerAdapter
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class TagsAdapter: RecyclerAdapter<AdapterTagModel, RecyclerViewHolder<AdapterTagModel>>() {

    override fun getViewType(model: AdapterTagModel) = model.type

    override fun getResId(viewType: Int) = if (viewType == 3) R.layout.tags_add_cell else R.layout.tags_cell

    override fun getViewHolder(view: View, viewType: Int) = if (viewType == 3) AddViewHolder(view) else TagsViewHolder(view)

}