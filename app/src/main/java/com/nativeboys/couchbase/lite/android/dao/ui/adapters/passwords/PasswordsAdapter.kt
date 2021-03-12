package com.nativeboys.couchbase.lite.android.dao.ui.adapters.passwords

import android.view.View
import com.nativeboys.couchbase.lite.android.dao.R
import com.nativeboys.couchbase.lite.android.dao.data.PasswordModel
import com.nativeboys.couchbase.lite.android.dao.helper.rv.RecyclerAdapter

class PasswordsAdapter : RecyclerAdapter<PasswordModel, PasswordsViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.passwords_cell

    override fun getViewHolder(view: View, viewType: Int) = PasswordsViewHolder(view)

}