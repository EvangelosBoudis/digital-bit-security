package com.zeustech.zeuskit.ui.other

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun ImageView.tintColor(@ColorInt color: Int) = setColorFilter(color, PorterDuff.Mode.MULTIPLY)

fun ImageView.vectorTintColor(@ColorInt color: Int) = setColorFilter(color, PorterDuff.Mode.SRC_IN)

fun ImageView.tintResColor(@ColorRes color: Int) = tintColor(ContextCompat.getColor(context, color))

fun ImageView.vectorTintResColor(@ColorRes color: Int) = vectorTintColor(ContextCompat.getColor(context, color))

fun TextView.drawableTintColor(@ColorInt color: Int, direction: Int) {
    compoundDrawablesRelative[direction]?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
}

fun TextView.drawableTintResColor(@ColorRes color: Int, direction: Int) = drawableTintColor(ContextCompat.getColor(context, color), direction)
