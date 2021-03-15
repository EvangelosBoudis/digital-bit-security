package com.zeustech.zeuskit.ui.autocomplete

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet

class InstantAutoComplete : AutoCompleteSearchView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun enoughToFilter() = true

    override fun onFocusChanged(
        focused: Boolean, direction: Int,
        previouslyFocusedRect: Rect?
    ) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) showDropDown()
    }

}