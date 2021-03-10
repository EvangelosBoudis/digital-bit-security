package com.nativeboys.couchbase.lite.android.dao.helper.other

import android.view.View

interface AdapterClickListener<T> {
    fun onClick(view: View, model: T, position: Int)
}