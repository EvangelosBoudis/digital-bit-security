package com.zeustech.zeuskit.ui.views

import android.widget.EditText

interface TextFieldListener {
    fun onTextChanged(editText: EditText, text: String)
    fun onActionChanged(
        editText: EditText,
        text: String,
        action: Int
    )
    fun onFocusChanged(
        editText: EditText,
        text: String,
        hasFocus: Boolean
    )
}