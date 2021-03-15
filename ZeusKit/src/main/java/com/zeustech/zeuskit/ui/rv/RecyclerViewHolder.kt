package com.zeustech.zeuskit.ui.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class RecyclerViewHolder<T>(itemView: View) :

    ViewHolder(itemView), View.OnClickListener {

    var clickListener: ViewHolderClickListener? = null

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val position = bindingAdapterPosition
        clickListener?.let {
            if (position != RecyclerView.NO_POSITION) it.onClick(v, position)
        }
    }

    abstract fun bind(model: T)

}