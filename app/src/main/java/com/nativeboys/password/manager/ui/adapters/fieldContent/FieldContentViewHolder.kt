package com.nativeboys.password.manager.ui.adapters.fieldContent

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldContentDto
import com.nativeboys.password.manager.other.InputTypeItem
import com.nativeboys.password.manager.other.findByCode
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

interface FieldContentTextChangeListener {
    fun onContentChanged(contentId: String, textContent: String)
}

class FieldContentViewHolder(
    itemView: View,
    private val fieldContentTextChangeListener: FieldContentTextChangeListener
) : RecyclerViewHolder<FieldContentDto>(itemView) {

    private val nameField = itemView.findViewById<TextView>(R.id.name_field)
    private val contentField = itemView.findViewById<EditText>(R.id.content_field)
    private val fieldBtn = itemView.findViewById<ImageView>(R.id.field_btn)

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
        contentField.addTextChangedListener {
            val text = it?.toString() ?: return@addTextChangedListener
            fieldContentTextChangeListener.onContentChanged(model.contentId, text)
        }
    }

}

//Regex("^(?=.*[A-Z].*[A-Z])(?=.*[!@#\$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}\$").matches("")
//https://stackoverflow.com/questions/5142103/regex-to-validate-password-strength
//https://github.com/zeustechgr/doo-Mobile-And/blob/master/app/src/main/java/com/zeustech/doo/ui/book/checkout/CheckoutFragment.kt