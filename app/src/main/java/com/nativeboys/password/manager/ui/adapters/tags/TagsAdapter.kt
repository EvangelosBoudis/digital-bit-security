package com.nativeboys.password.manager.ui.adapters.tags

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.TagDto
import com.nativeboys.password.manager.ui.adapters.AddViewHolder
import com.nativeboys.uikit.rv.RecyclerListAdapter
import com.nativeboys.uikit.rv.RecyclerViewHolder

class TagsAdapter: RecyclerListAdapter<TagDto, RecyclerViewHolder<TagDto>>() {

    override fun getViewType(model: TagDto) = model.type

    override fun getResId(viewType: Int) = if (viewType == 3) R.layout.description_add_cell else R.layout.description_cell

    override fun getViewHolder(view: View, viewType: Int) = if (viewType == 3) AddViewHolder(view) else TagsViewHolder(view)

}