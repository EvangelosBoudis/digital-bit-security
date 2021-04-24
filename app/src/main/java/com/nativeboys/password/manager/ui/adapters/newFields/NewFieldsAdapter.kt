package com.nativeboys.password.manager.ui.adapters.newFields

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.UIField
import com.nativeboys.password.manager.ui.adapters.AddViewHolder
import com.nativeboys.password.manager.ui.adapters.EditTextChangeListener
import com.nativeboys.password.manager.ui.adapters.FieldTextChangeListener
import com.zeustech.zeuskit.ui.rv.RecyclerListAdapter
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class NewFieldsAdapter(
    private val fieldTextChangeListener: FieldTextChangeListener
) : RecyclerListAdapter<UIField, RecyclerViewHolder<UIField>>(), EditTextChangeListener {

    override fun getViewType(model: UIField) = model.cellType

    override fun getResId(viewType: Int) = if (viewType == 1) R.layout.new_field_cell else R.layout.new_field_add_cell

    override fun getViewHolder(view: View, viewType: Int) = if (viewType == 1) NewFieldsViewHolder(view, this) else AddViewHolder(view)

    override fun onChange(position: Int, textContent: String) {
        fieldTextChangeListener.onChange(getItem(position).id, textContent)
    }

}