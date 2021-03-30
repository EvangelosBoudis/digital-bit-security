package com.zeustech.zeuskit.ui.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.zeustech.zeuskit.R

class BottomBar(
    parent: ViewGroup,
    @LayoutRes val resLayout: Int,
    @BaseTransientBottomBar.Duration duration: Int
) {

    private val bar: Snackbar = Snackbar.make(parent, "", duration)

    init {
        val layout = bar.view as? SnackbarLayout
        val textView = layout?.findViewById<View>(R.id.snackbar_text) as? TextView
        textView?.visibility = View.INVISIBLE
        val snackView = LayoutInflater.from(parent.context).inflate(resLayout, parent, false)
        layout?.setPadding(0, 0, 0, 0)
        layout?.addView(snackView, 0)
        bar.show()
    }

    fun show() {
        bar.show()
    }

}