package com.nativeboys.uikit.rv

interface ListAdapterItem<T> {
    fun areItemsTheSame(model: T): Boolean
    fun areContentsTheSame(model: T): Boolean
}