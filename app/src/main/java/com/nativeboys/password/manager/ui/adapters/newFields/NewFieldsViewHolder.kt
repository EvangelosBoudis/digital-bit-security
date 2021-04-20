package com.nativeboys.password.manager.ui.adapters.newFields

import android.view.View
import android.widget.EditText
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldData
import com.nativeboys.password.manager.util.allTypes
import com.nativeboys.password.manager.util.findByCode
import com.nativeboys.password.manager.ui.adapters.types.TypesAdapter
import com.zeustech.zeuskit.ui.autocomplete.DescriptionModel
import com.zeustech.zeuskit.ui.autocomplete.InstantAutoComplete
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class NewFieldsViewHolder(itemView: View) : RecyclerViewHolder<FieldData>(itemView) {

    private val contentField = itemView.findViewById<EditText>(R.id.content_field)
    private val spinnerView = itemView.findViewById<InstantAutoComplete>(R.id.spinner_view)
    private val typesAdapter = TypesAdapter(itemView.context)

    override fun bind(model: FieldData) {
        contentField.setText(model.name)
        spinnerView.setText(model.type)
        spinnerView.setAdapter(typesAdapter)
        typesAdapter.dataSet = allTypes().map {
            return@map object : DescriptionModel {
                override fun getText() = it.description
            }
        }
        spinnerView.setText(findByCode(model.type)?.description)
    }

}