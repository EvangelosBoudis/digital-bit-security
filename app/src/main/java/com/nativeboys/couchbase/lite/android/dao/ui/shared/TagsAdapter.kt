package com.nativeboys.couchbase.lite.android.dao.ui.shared

import android.view.View
import com.nativeboys.couchbase.lite.android.dao.R
import com.nativeboys.couchbase.lite.android.dao.data.TagModel
import com.nativeboys.couchbase.lite.android.dao.helper.rv.RecyclerAdapter

class TagsAdapter: RecyclerAdapter<TagModel, TagsViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.tag_cell

    override fun getViewHolder(view: View, viewType: Int) = TagsViewHolder(view)

}