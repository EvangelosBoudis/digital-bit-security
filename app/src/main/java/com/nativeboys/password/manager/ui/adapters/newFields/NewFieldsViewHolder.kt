package com.nativeboys.password.manager.ui.adapters.newFields

import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import com.google.android.material.switchmaterial.SwitchMaterial
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldData
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class NewFieldsViewHolder(itemView: View) : RecyclerViewHolder<FieldData>(itemView) {

    private val contentField = itemView.findViewById<EditText>(R.id.content_field)
    private val typeBtn = itemView.findViewById<CheckBox>(R.id.type_btn)
    private val visibilityBtn = itemView.findViewById<SwitchMaterial>(R.id.visibility_btn)

    override fun bind(model: FieldData) {
        contentField.setText(model.name)
        typeBtn.isChecked = model.type == 1
        visibilityBtn.isChecked = model.hidden
    }

}