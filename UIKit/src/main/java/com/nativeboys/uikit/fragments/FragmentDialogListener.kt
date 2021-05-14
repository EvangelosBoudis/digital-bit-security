package com.nativeboys.uikit.fragments

import android.view.View
import androidx.fragment.app.DialogFragment

interface FragmentDialogListener {
    fun onClick(dialogFragment: DialogFragment, view: View)
}