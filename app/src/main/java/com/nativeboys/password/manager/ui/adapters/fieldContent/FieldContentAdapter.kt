package com.nativeboys.password.manager.ui.adapters.fieldContent

import android.view.View
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldContentDto
import com.nativeboys.password.manager.ui.adapters.EditTextChangeListener
import com.nativeboys.password.manager.ui.adapters.FieldTextChangeListener
import com.zeustech.zeuskit.ui.rv.RecyclerListAdapter

class FieldContentAdapter(
    private val fieldTextChangeListener: FieldTextChangeListener
) : RecyclerListAdapter<FieldContentDto, FieldContentViewHolder>(), EditTextChangeListener {

    override fun getResId(viewType: Int) = R.layout.field_content_cell

    override fun getViewHolder(view: View, viewType: Int) = FieldContentViewHolder(view, this)

    override fun onChange(position: Int, textContent: String) {
        fieldTextChangeListener.onChange(getItem(position).fieldId, textContent)
    }

}