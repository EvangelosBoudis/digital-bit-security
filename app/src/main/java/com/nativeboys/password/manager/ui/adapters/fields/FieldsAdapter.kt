package com.nativeboys.password.manager.ui.adapters.fields

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldContentModel
import com.zeustech.zeuskit.ui.rv.RecyclerAdapter

class FieldsAdapter : RecyclerAdapter<FieldContentModel, FieldsViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.fields_cell

    override fun getViewHolder(view: View, viewType: Int) = FieldsViewHolder(view)

}