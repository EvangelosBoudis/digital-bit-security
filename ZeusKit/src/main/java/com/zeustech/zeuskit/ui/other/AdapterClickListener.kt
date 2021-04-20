package com.zeustech.zeuskit.ui.other

import android.view.View

interface AdapterClickListener<T> {
    fun onClick(view: View, model: T, position: Int)
    fun onLongClick(view: View, model: T, position: Int) {  }
}