package com.nativeboys.couchbase.lite.android.dao.helper.rv

import android.view.View

interface ViewHolderClickListener {
    fun onClick(view: View, index: Int)
}