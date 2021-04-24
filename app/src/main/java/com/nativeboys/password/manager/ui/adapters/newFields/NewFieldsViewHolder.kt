package com.nativeboys.password.manager.ui.adapters.newFields

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.UIField
import com.nativeboys.password.manager.ui.adapters.EditTextChangeListener
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class NewFieldsViewHolder(
    itemView: View,
    private val editTextChangeListener: EditTextChangeListener
) : RecyclerViewHolder<UIField>(itemView), TextWatcher {

    private val contentField = itemView.findViewById<EditText>(R.id.content_field)
    private val typeBtn = itemView.findViewById<Button>(R.id.type_btn)
    private val deleteBtn = itemView.findViewById<Button>(R.id.delete_btn)

    init {
        typeBtn.setOnClickListener(this)
        deleteBtn.setOnClickListener(this)
        contentField.addTextChangedListener(this)
    }

    override fun bind(model: UIField) {
        contentField.setText(model.name)
        model.typeDescription?.let {
            typeBtn.text = it
        } ?: run {
            typeBtn.setText(R.string.type)
        }
        // Work Around for -> java.lang.IllegalStateException: focus search returned a view that wasn't able to take focus!
        contentField.setOnEditorActionListener { v, _, _ ->
            val nextView = v.focusSearch(View.FOCUS_DOWN)
            nextView?.requestFocus(View.FOCUS_DOWN)
            return@setOnEditorActionListener true
        }
    }

    override fun afterTextChanged(editable: Editable?) {
        editTextChangeListener.onChange(bindingAdapterPosition, editable?.toString() ?: "")
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

}