package com.nativeboys.password.manager.ui.adapters.newFields

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldData
import com.zeustech.zeuskit.ui.rv.RecyclerAdapter

class NewFieldsAdapter : RecyclerAdapter<FieldData, NewFieldsViewHolder>() {

    override fun getResId(viewType: Int) = R.layout.new_fields_cell

    override fun getViewHolder(view: View, viewType: Int) = NewFieldsViewHolder(view)

}