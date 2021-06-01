package com.nativeboys.uikit.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class RecyclerViewHolder<T>(
    itemView: View
) : ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

    var clickListener: ViewHolderClickListener? = null

    init {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    override fun onClick(v: View) {
        val position = bindingAdapterPosition
        clickListener?.let {
            if (position != RecyclerView.NO_POSITION) it.onClick(v, position)
        }
    }

    override fun onLongClick(v: View): Boolean {
        val position = bindingAdapterPosition
        clickListener?.let {
            if (position != RecyclerView.NO_POSITION) it.onLongClick(v, position)
        }
        return true
    }

    abstract fun bind(model: T)

}