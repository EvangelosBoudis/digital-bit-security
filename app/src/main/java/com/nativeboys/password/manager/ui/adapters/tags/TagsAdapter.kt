package com.nativeboys.password.manager.ui.adapters.tags

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.TagDto
import com.nativeboys.password.manager.ui.adapters.AddViewHolder
import com.zeustech.zeuskit.ui.rv.RecyclerListAdapter
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class TagsAdapter: RecyclerListAdapter<TagDto, RecyclerViewHolder<TagDto>>() {

    override fun getViewType(model: TagDto) = model.type

    override fun getResId(viewType: Int) = if (viewType == 3) R.layout.descriptions_add_cell else R.layout.tags_cell

    override fun getViewHolder(view: View, viewType: Int) = if (viewType == 3) AddViewHolder(view) else TagsViewHolder(view)

}