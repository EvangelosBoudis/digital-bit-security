package com.nativeboys.password.manager.ui.adapters.fields

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldContentModel
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class FieldsViewHolder(itemView: View) : RecyclerViewHolder<FieldContentModel>(itemView) {

    private val nameField = itemView.findViewById<TextView>(R.id.name_field)
    private val contentField = itemView.findViewById<EditText>(R.id.content_field)
    private val copyBtn = itemView.findViewById<ImageView>(R.id.copy_btn)

    init {
        copyBtn.setOnClickListener(this)
    }

    override fun bind(model: FieldContentModel) {
        nameField.text = model.name
        contentField.setText(model.content)
        contentField.transformationMethod = if (model.hidden) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
    }

}