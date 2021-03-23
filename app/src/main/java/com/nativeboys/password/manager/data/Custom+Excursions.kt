package com.nativeboys.password.manager.data

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.widget.EditText
import com.google.android.flexbox.*

fun FlexboxLayoutManager.wrapCells() {
    this.flexWrap = FlexWrap.WRAP
    this.flexDirection = FlexDirection.ROW
    this.justifyContent = JustifyContent.FLEX_START
    this.alignItems = AlignItems.FLEX_START
}

fun EditText.toggleTransformationMethod() {
    val hidden = this.transformationMethod is PasswordTransformationMethod
    val updatedMethod: TransformationMethod = if (hidden) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
    this.transformationMethod = updatedMethod
    this.setSelection(this.length())
}

fun EditText.setTransformationMethodAsHidden(hidden: Boolean) {
    this.transformationMethod = if (hidden) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
}