package com.nativeboys.couchbase.lite.android.dao.ui.adapters.tags

import android.view.View
import com.nativeboys.couchbase.lite.android.dao.R
import com.nativeboys.couchbase.lite.android.dao.data.AdapterTagModel
import com.nativeboys.couchbase.lite.android.dao.helper.rv.RecyclerAdapter
import com.nativeboys.couchbase.lite.android.dao.helper.rv.RecyclerViewHolder
import com.nativeboys.couchbase.lite.android.dao.ui.adapters.AddViewHolder

class TagsAdapter: RecyclerAdapter<AdapterTagModel, RecyclerViewHolder<AdapterTagModel>>() {

    override fun getViewType(model: AdapterTagModel) = model.type

    override fun getResId(viewType: Int) = if (viewType == 3) R.layout.tags_add_cell else R.layout.tags_cell

    override fun getViewHolder(view: View, viewType: Int) = if (viewType == 3) AddViewHolder(view) else TagsViewHolder(view)

}