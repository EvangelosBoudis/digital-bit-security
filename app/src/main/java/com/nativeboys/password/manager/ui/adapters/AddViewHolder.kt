package com.nativeboys.password.manager.ui.adapters

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.nativeboys.password.manager.R
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class AddViewHolder<T> (itemView: View) : RecyclerViewHolder<T>(itemView) {

    private val addBtn = itemView.findViewById<ImageView>(R.id.add_btn)

    init {
        addBtn.setOnClickListener(this)
    }

    override fun bind(model: T) {
        Glide.with(itemView.context)
            .load(R.drawable.ic_baseline_add_24)
            .into(addBtn)
    }

}