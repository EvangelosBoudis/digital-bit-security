package com.nativeboys.couchbase.lite.android.dao.ui.adapters.thumbnails

import android.view.View
import com.nativeboys.couchbase.lite.android.dao.R
import com.nativeboys.couchbase.lite.android.dao.data.AdapterThumbnailModel
import com.nativeboys.couchbase.lite.android.dao.helper.rv.RecyclerAdapter
import com.nativeboys.couchbase.lite.android.dao.helper.rv.RecyclerViewHolder
import com.nativeboys.couchbase.lite.android.dao.ui.adapters.AddViewHolder

class ThumbnailsAdapter : RecyclerAdapter<AdapterThumbnailModel, RecyclerViewHolder<AdapterThumbnailModel>>() {

    override fun getViewType(model: AdapterThumbnailModel) = model.type

    override fun getResId(viewType: Int) = if (viewType == 3) R.layout.thumbnails_add_cell else R.layout.thumbnail_cell

    override fun getViewHolder(view: View, viewType: Int) =  if (viewType == 3) AddViewHolder(view) else ThumbnailsViewHolder(view)

}