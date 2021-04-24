package com.nativeboys.password.manager.ui.adapters

interface EditTextChangeListener {
    fun onChange(position: Int, textContent: String)
}

interface FieldTextChangeListener {
    fun onChange(fieldId: String, textContent: String)
}