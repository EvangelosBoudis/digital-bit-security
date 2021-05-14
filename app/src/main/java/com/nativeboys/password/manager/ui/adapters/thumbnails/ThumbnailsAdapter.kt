package com.nativeboys.password.manager.ui.adapters.thumbnails

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.password.manager.ui.adapters.AddViewHolder
import com.nativeboys.uikit.rv.RecyclerListAdapter
import com.nativeboys.uikit.rv.RecyclerViewHolder

class ThumbnailsAdapter : RecyclerListAdapter<ThumbnailDto, RecyclerViewHolder<ThumbnailDto>>() {

    override fun getViewType(model: ThumbnailDto) = model.type

    override fun getResId(viewType: Int) = if (viewType == 3) R.layout.thumbnail_add_cell else R.layout.thumbnail_cell

    override fun getViewHolder(view: View, viewType: Int) =  if (viewType == 3) AddViewHolder(view) else ThumbnailsViewHolder(view)

}