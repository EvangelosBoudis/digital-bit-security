package com.nativeboys.password.manager.ui.adapters.searchPasswords

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.PasswordModel
import com.zeustech.zeuskit.ui.rv.RecyclerAdapter

class SearchPasswordsAdapter : RecyclerAdapter<PasswordModel, SearchPasswordsViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.search_passwords_cell

    override fun getViewHolder(view: View, viewType: Int) = SearchPasswordsViewHolder(view)

}