package com.zeustech.zeuskit.ui.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.widget.AppCompatEditText

open class TextField : AppCompatEditText, TextWatcher, OnEditorActionListener, OnFocusChangeListener {

    var textFieldListener: TextFieldListener? = null

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
        addTextChangedListener(this)
        setOnEditorActionListener(this)
        onFocusChangeListener = this
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
    }

    override fun afterTextChanged(s: Editable) {
        textFieldListener?.onTextChanged(this, s.toString())
    }

    override fun onEditorAction(
        v: TextView,
        actionId: Int,
        event: KeyEvent
    ): Boolean {
        textFieldListener?.onActionChanged(
            this,
            (text?.toString() ?: ""),
            actionId
        )
        return actionId == EditorInfo.IME_ACTION_DONE
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        textFieldListener?.onFocusChanged(
            this,
            (text?.toString() ?: ""),
            hasFocus
        )
    }
}