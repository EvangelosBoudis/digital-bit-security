package com.nativeboys.password.manager.util

import android.view.View
import androidx.fragment.app.DialogFragment

interface FragmentDialogListener {
    fun onClick(dialogFragment: DialogFragment, view: View)
}