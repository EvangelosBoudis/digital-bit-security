package com.nativeboys.couchbase.lite.android.dao.helper.rv

interface ListAdapterItem<T> {
    fun areItemsTheSame(model: T): Boolean
    fun areContentsTheSame(model: T): Boolean
}