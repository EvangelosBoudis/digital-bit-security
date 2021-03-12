package com.nativeboys.couchbase.lite.android.dao.ui.adapters

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.nativeboys.couchbase.lite.android.dao.R
import com.nativeboys.couchbase.lite.android.dao.helper.rv.RecyclerViewHolder

class AddViewHolder<T> (itemView: View) : RecyclerViewHolder<T>(itemView) {

    private val addBtn = itemView.findViewById<ImageView>(R.id.add_btn)

    init {
        addBtn.setOnClickListener(this)
    }

    override fun bind(model: T) {
        Glide.with(itemView.context)
            .load(R.drawable.plus_icon_2)
            .into(addBtn)
    }

}