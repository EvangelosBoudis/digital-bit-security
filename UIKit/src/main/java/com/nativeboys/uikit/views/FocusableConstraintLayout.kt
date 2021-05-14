package com.nativeboys.uikit.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.constraintlayout.widget.ConstraintLayout
import com.nativeboys.uikit.other.KeyboardManager

class FocusableConstraintLayout : ConstraintLayout, OnFocusChangeListener {

    constructor(context: Context?) : super(context!!) {
        setUpView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
        setUpView()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context!!, attrs, defStyleAttr) {
        setUpView()
    }

    private fun setUpView() {
        isFocusable = true
        isClickable = true
        isFocusableInTouchMode = true
        onFocusChangeListener = this
    }

    override fun onFocusChange(view: View, b: Boolean) {
        if (b) KeyboardManager().hideKeyboard(view)
    }

}