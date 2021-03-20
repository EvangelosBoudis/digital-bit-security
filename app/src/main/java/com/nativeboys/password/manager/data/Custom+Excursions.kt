package com.nativeboys.password.manager.data

import com.google.android.flexbox.*

fun FlexboxLayoutManager.wrapCells() {
    this.flexWrap = FlexWrap.WRAP
    this.flexDirection = FlexDirection.ROW
    this.justifyContent = JustifyContent.FLEX_START
    this.alignItems = AlignItems.FLEX_START
}