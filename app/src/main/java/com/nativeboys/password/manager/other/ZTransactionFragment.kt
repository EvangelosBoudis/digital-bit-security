package com.nativeboys.password.manager.other

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class ZTransactionFragment(@LayoutRes contentLayoutId: Int): Fragment(contentLayoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyZTransition()
    }

}
