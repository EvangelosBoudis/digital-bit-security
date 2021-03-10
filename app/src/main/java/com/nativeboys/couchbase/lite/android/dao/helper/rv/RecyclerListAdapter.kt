package com.nativeboys.couchbase.lite.android.dao.helper.rv

import androidx.recyclerview.widget.DiffUtil
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.ListAdapter
import com.nativeboys.couchbase.lite.android.dao.helper.other.AdapterClickListener

abstract class RecyclerListAdapter<T : ListAdapterItem<T>, VH : RecyclerViewHolder<T>> protected constructor() :
    ListAdapter<T, VH>(object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(model: T, t1: T): Boolean {
            return model.areItemsTheSame(t1)
        }

        override fun areContentsTheSame(model: T, t1: T): Boolean {
            return model.areContentsTheSame(t1)
        }
    }), ViewHolderClickListener {

    var adapterClickListener: AdapterClickListener<T>? = null

    override fun getItemViewType(position: Int): Int {
        return  getItem(position)?.let { getViewType(it) } ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view =
            LayoutInflater.from(parent.context).inflate(getResId(viewType), parent, false)
        return getViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
        holder.clickListener = this
    }

    override fun onClick(view: View, index: Int) {
        adapterClickListener?.onClick(view, getItem(index), index)
    }

    protected open fun getViewType(model: T): Int {
        return 0
    }

    @LayoutRes
    protected abstract fun getResId(viewType: Int): Int
    protected abstract fun getViewHolder(view: View, viewType: Int): VH
}