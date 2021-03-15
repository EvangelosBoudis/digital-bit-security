package com.zeustech.zeuskit.ui.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

abstract class SpinnerAdapter<T>(
    context: Context,
    private val parentLayout: Int,
    private val childLayout: Int
) : ArrayAdapter<T>(context, 0) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val cv: View = convertView ?: LayoutInflater.from(context).inflate(parentLayout, parent, false)
        onBindParent(getItem(position), cv)
        return cv
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val cv: View = convertView ?: LayoutInflater.from(context).inflate(childLayout, parent, false)
        onBindChild(getItem(position), cv)
        return cv
    }

    abstract fun onBindParent(model: T?, viewHolder: View)
    abstract fun onBindChild(model: T?, viewHolder: View)

    fun setDataSet(dataSet: List<T>) {
        clear()
        addAll(dataSet)
        notifyDataSetChanged()
    }

}