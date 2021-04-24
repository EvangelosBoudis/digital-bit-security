package com.nativeboys.password.manager.ui.adapters.fieldContent

import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldContentDto
import com.nativeboys.password.manager.ui.adapters.EditTextChangeListener
import com.nativeboys.password.manager.util.InputTypeItem
import com.nativeboys.password.manager.util.findByCode
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class FieldContentViewHolder(
    itemView: View,
    private val editTextChangeListener: EditTextChangeListener
) : RecyclerViewHolder<FieldContentDto>(itemView), TextWatcher {

    private val nameField = itemView.findViewById<TextView>(R.id.name_field)
    private val contentField = itemView.findViewById<EditText>(R.id.content_field)
    private val fieldBtn = itemView.findViewById<ImageView>(R.id.field_btn)

    init {
        contentField.addTextChangedListener(this)
    }

    override fun bind(model: FieldContentDto) {
        nameField.text = model.fieldName
        contentField.setText(model.textContent)
        val hidden = model.fieldType == InputTypeItem.TEXT_PASSWORD.code
        Glide
            .with(itemView.context)
            .load(if (hidden) R.drawable.ic_outline_visibility_24 else R.drawable.ic_baseline_cancel_24)
            .into(fieldBtn)
        findByCode(model.fieldType)?.type?.let {
            contentField.inputType = it
        }
        fieldBtn.setOnClickListener {
            if (hidden) { // Hide-Show Password
                val hiddenState = contentField.transformationMethod is PasswordTransformationMethod
                val updatedMethod: TransformationMethod = if (hiddenState) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
                contentField.transformationMethod = updatedMethod
                contentField.setSelection(contentField.length())
            } else { // Erase content
                contentField.text = null
            }
        }
    }

    override fun afterTextChanged(editable: Editable?) {
        editTextChangeListener.onChange(bindingAdapterPosition, editable?.toString() ?: "")
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

}