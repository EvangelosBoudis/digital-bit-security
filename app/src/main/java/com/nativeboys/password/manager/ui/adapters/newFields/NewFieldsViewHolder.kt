package com.nativeboys.password.manager.ui.adapters.newFields

import android.view.View
import android.widget.Button
import android.widget.EditText
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldData
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class NewFieldsViewHolder(itemView: View) : RecyclerViewHolder<FieldData>(itemView) {

    private val contentField = itemView.findViewById<EditText>(R.id.content_field)
    private val typeBtn = itemView.findViewById<Button>(R.id.type_btn)

    override fun bind(model: FieldData) {
        contentField.setText(model.name)
        typeBtn.setOnClickListener(this)

        // Work Around for -> java.lang.IllegalStateException: focus search returned a view that wasn't able to take focus!
        contentField.setOnEditorActionListener { v, _, _ ->
            val nextView = v.focusSearch(View.FOCUS_DOWN)
            nextView?.requestFocus(View.FOCUS_DOWN)
            return@setOnEditorActionListener true
        }
    }

}