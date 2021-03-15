package com.zeustech.zeuskit.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class SwipeViewPager : ViewPager {

    var isPagingEnabled = true

    constructor(context: Context?) : super(context!!)

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return isPagingEnabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return isPagingEnabled && super.onInterceptTouchEvent(event)
    }

    fun navigate(type: Int) {
        val allItems = adapter?.count ?: return
        if (type == 1) { // Next
            if (++currentItem < allItems) {
                currentItem = currentItem
            }
        } else if (--currentItem >= 0) { // Prev
            currentItem = currentItem
        }
    }

}