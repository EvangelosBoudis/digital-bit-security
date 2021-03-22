package com.nativeboys.password.manager.ui.adapters.fieldContent

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldContentModel
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class FieldContentViewHolder(itemView: View) : RecyclerViewHolder<FieldContentModel>(itemView) {

    private val nameField = itemView.findViewById<TextView>(R.id.name_field)
    private val contentField = itemView.findViewById<EditText>(R.id.content_field)
    private val fieldBtn = itemView.findViewById<ImageView>(R.id.field_btn)

    override fun bind(model: FieldContentModel) {
        nameField.text = model.name
        contentField.setText(model.content)
        Glide
            .with(itemView.context)
            .load(if (model.hidden) R.drawable.visibility_icon else R.drawable.remove_circle_icon)
            .into(fieldBtn)
        fieldBtn.setOnClickListener {
            if (model.hidden) { // Hide-Show Password
                val hidden = contentField.transformationMethod is PasswordTransformationMethod
                val updatedMethod: TransformationMethod = if (hidden) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
                contentField.transformationMethod = updatedMethod
                contentField.setSelection(contentField.length())
            } else { // Erase content
                contentField.text = null
            }
        }
    }

}