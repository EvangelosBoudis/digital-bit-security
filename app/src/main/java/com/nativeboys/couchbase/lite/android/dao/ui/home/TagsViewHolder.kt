package com.nativeboys.couchbase.lite.android.dao.ui.home

import android.view.View
import android.widget.TextView
import com.nativeboys.couchbase.lite.android.dao.R
import com.nativeboys.couchbase.lite.android.dao.data.TagModel
import com.nativeboys.couchbase.lite.android.dao.helper.rv.RecyclerViewHolder

class TagsViewHolder(itemView: View): RecyclerViewHolder<TagModel>(itemView) {

    private val descriptionField = itemView.findViewById<TextView>(R.id.description_field)

    override fun bind(model: TagModel) {
        descriptionField.text = model.description
    }

}