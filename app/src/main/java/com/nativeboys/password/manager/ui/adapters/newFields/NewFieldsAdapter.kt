package com.nativeboys.password.manager.ui.adapters.newFields

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldEntity
import com.nativeboys.password.manager.ui.adapters.AddViewHolder
import com.zeustech.zeuskit.ui.rv.RecyclerAdapter
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class NewFieldsAdapter : RecyclerAdapter<FieldEntity, RecyclerViewHolder<FieldEntity>>() {

    override fun getViewType(model: FieldEntity) = model.type

    override fun getResId(viewType: Int) = if (viewType != -1) R.layout.new_field_cell else R.layout.new_field_add_cell

    override fun getViewHolder(view: View, viewType: Int) = if (viewType != -1) NewFieldsViewHolder(view) else AddViewHolder(view)

}