package com.nativeboys.password.manager.ui.adapters.passwords

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.PasswordModel
import com.zeustech.zeuskit.ui.rv.RecyclerAdapter

class PasswordsAdapter : RecyclerAdapter<PasswordModel, PasswordsViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.passwords_cell

    override fun getViewHolder(view: View, viewType: Int) = PasswordsViewHolder(view)

}