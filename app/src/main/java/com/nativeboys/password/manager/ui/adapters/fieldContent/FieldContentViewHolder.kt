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
import com.nativeboys.password.manager.other.FieldContentModel
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

        //Regex("^(?=.*[A-Z].*[A-Z])(?=.*[!@#\$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}\$").matches("")
        //https://stackoverflow.com/questions/5142103/regex-to-validate-password-strength
        //https://github.com/zeustechgr/doo-Mobile-And/blob/master/app/src/main/java/com/zeustech/doo/ui/book/checkout/CheckoutFragment.kt

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