package com.zeustech.zeuskit.ui.views

import android.app.Activity
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

class ToastMessage(
    context: Context,
    @LayoutRes layoutRes: Int,
    @IdRes rootId: Int,
    @IdRes tvId: Int
) : Toast(context) {

    private val background: GradientDrawable
    private val textView: TextView

    init {
        val activity = context as Activity
        val inflater = activity.layoutInflater
        val layout =
            inflater.inflate(layoutRes, activity.findViewById(rootId))
        textView = layout.findViewById(tvId)
        background = GradientDrawable()
        background.shape = GradientDrawable.RECTANGLE
        textView.background = background
        setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 15)
        duration = LENGTH_SHORT
        view = layout
    }

    fun setBackgroundColor(color: Int): ToastMessage {
        background.setColor(color)
        return this
    }

    fun setTextColor(color: Int): ToastMessage {
        textView.setTextColor(color)
        return this
    }

    fun setBackgroundCornerRadius(radius: Int): ToastMessage {
        background.cornerRadius = radius.toFloat()
        return this
    }

    fun setText(text: String?): ToastMessage {
        textView.text = text
        return this
    }

}