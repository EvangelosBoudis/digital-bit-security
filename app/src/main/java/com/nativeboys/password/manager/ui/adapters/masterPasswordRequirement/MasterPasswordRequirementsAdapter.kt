package com.nativeboys.password.manager.ui.adapters.masterPasswordRequirement

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.UIMasterPasswordRequirement
import com.nativeboys.uikit.rv.RecyclerListAdapter

class MasterPasswordRequirementsAdapter : RecyclerListAdapter<UIMasterPasswordRequirement, MasterPasswordRequirementsViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.description_cell

    override fun getViewHolder(view: View, viewType: Int) = MasterPasswordRequirementsViewHolder(view)

}