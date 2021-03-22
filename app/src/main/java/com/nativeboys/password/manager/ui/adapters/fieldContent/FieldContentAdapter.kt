package com.nativeboys.password.manager.ui.adapters.fieldContent

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldContentModel
import com.zeustech.zeuskit.ui.rv.RecyclerAdapter

class FieldContentAdapter : RecyclerAdapter<FieldContentModel, FieldContentViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.field_content_cell

    override fun getViewHolder(view: View, viewType: Int) = FieldContentViewHolder(view)

}