package com.nativeboys.password.manager.ui.adapters.tags

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.TagModel
import com.nativeboys.password.manager.ui.adapters.AddViewHolder
import com.zeustech.zeuskit.ui.rv.RecyclerAdapter
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class TagsAdapter: RecyclerAdapter<TagModel, RecyclerViewHolder<TagModel>>() {

    override fun getViewType(model: TagModel) = model.type

    override fun getResId(viewType: Int) = if (viewType == 3) R.layout.add_descriptions_cell else R.layout.descriptions_cell

    override fun getViewHolder(view: View, viewType: Int) = if (viewType == 3) AddViewHolder(view) else TagsViewHolder(view)

}