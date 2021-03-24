package com.nativeboys.password.manager.ui.adapters.newFields

import android.view.View
import android.widget.EditText
import android.widget.Spinner
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldData
import com.nativeboys.password.manager.data.InputTypeItem
import com.nativeboys.password.manager.ui.adapters.types.TypesAdapter
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class NewFieldsViewHolder(itemView: View) : RecyclerViewHolder<FieldData>(itemView) {

    private val contentField = itemView.findViewById<EditText>(R.id.content_field)
    private val spinnerView = itemView.findViewById<Spinner>(R.id.spinner_view)
    private var typesAdapter: TypesAdapter = TypesAdapter(itemView.context)

    override fun bind(model: FieldData) {
        contentField.setText(model.name)
        spinnerView.adapter = typesAdapter
        typesAdapter.setDataSet(
            InputTypeItem.getSubset(listOf(8, 0, 1, 7, 14, 20, 25, 30))
        )
    }

}