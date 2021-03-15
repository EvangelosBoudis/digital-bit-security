package com.zeustech.zeuskit.ui.rv

interface ListAdapterItem<T> {
    fun areItemsTheSame(model: T): Boolean
    fun areContentsTheSame(model: T): Boolean
}