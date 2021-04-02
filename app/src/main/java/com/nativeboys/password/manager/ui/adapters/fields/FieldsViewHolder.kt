package com.nativeboys.password.manager.ui.adapters.fields

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldContentDto
import com.nativeboys.password.manager.other.InputTypeItem
import com.nativeboys.password.manager.other.findByCode
import com.nativeboys.password.manager.other.toggleTransformationMethod
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class FieldsViewHolder(itemView: View) : RecyclerViewHolder<FieldContentDto>(itemView) {

    private val nameField = itemView.findViewById<TextView>(R.id.name_field)
    private val contentField = itemView.findViewById<EditText>(R.id.content_field)
    private val visibilityBtn = itemView.findViewById<ImageView>(R.id.visibility_btn)
    private val copyBtn = itemView.findViewById<ImageView>(R.id.copy_btn)

    init {
        visibilityBtn.setOnClickListener {
            contentField.toggleTransformationMethod()
        }
        copyBtn.setOnClickListener(this)
    }

    override fun bind(model: FieldContentDto) {
        nameField.text = model.fieldName
        contentField.setText(model.textContent)
        val hidden = model.fieldType == InputTypeItem.TEXT_PASSWORD.code
        if (hidden) {
            Glide
                .with(itemView.context)
                .load(R.drawable.visibility_icon)
                .into(visibilityBtn)
        }
        visibilityBtn.visibility = if(hidden) View.VISIBLE else View.GONE
        Glide
            .with(itemView.context)
            .load(R.drawable.copy_icon)
            .into(copyBtn)
        findByCode(model.fieldType)?.type?.let {
            contentField.inputType = it
        }
        //contentField.setTransformationMethodAsHidden(model.hidden)
    }

}

