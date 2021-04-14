package com.nativeboys.password.manager.ui.adapters.thumbnails

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.password.manager.ui.adapters.AddViewHolder
import com.zeustech.zeuskit.ui.rv.RecyclerListAdapter
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class ThumbnailsAdapter(private val longClickListener: OnThumbnailLongClickListener) : RecyclerListAdapter<ThumbnailDto, RecyclerViewHolder<ThumbnailDto>>() {

    override fun getViewType(model: ThumbnailDto) = model.type

    override fun getResId(viewType: Int) = if (viewType == 3) R.layout.thumbnails_add_cell else R.layout.thumbnails_cell

    override fun getViewHolder(view: View, viewType: Int) =  if (viewType == 3) AddViewHolder(view) else ThumbnailsViewHolder(view, longClickListener)

}