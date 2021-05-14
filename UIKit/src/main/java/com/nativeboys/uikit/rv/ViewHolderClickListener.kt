package com.nativeboys.uikit.rv

import android.view.View

interface ViewHolderClickListener {
    fun onClick(view: View, index: Int)
    fun onLongClick(view: View, index: Int)
}