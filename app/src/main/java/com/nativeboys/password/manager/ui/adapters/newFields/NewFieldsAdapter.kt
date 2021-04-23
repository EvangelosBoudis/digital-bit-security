package com.nativeboys.password.manager.ui.adapters.newFields

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.UIField
import com.nativeboys.password.manager.ui.adapters.AddViewHolder
import com.nativeboys.password.manager.ui.adapters.FieldContentTextChangeListener
import com.zeustech.zeuskit.ui.rv.RecyclerListAdapter
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class NewFieldsAdapter(
    private val fieldContentTextChangeListener: FieldContentTextChangeListener
) : RecyclerListAdapter<UIField, RecyclerViewHolder<UIField>>() {

    override fun getViewType(model: UIField) = model.cellType

    override fun getResId(viewType: Int) = if (viewType == 1) R.layout.new_field_cell else R.layout.new_field_add_cell

    override fun getViewHolder(view: View, viewType: Int) = if (viewType == 1) NewFieldsViewHolder(view, fieldContentTextChangeListener) else AddViewHolder(view)

}