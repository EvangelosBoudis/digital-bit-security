package com.nativeboys.password.manager.ui.adapters.newFields

import android.view.View
import android.widget.EditText
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FieldEntity
import com.nativeboys.password.manager.other.InputTypeItem
import com.nativeboys.password.manager.ui.adapters.types.TypesAdapter
import com.zeustech.zeuskit.ui.autocomplete.InstantAutoComplete
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class NewFieldsViewHolder(itemView: View) : RecyclerViewHolder<FieldEntity>(itemView) {

    private val contentField = itemView.findViewById<EditText>(R.id.content_field)
    private val spinnerView = itemView.findViewById<InstantAutoComplete>(R.id.spinner_view)
    private val typesAdapter = TypesAdapter(itemView.context)

    override fun bind(model: FieldEntity) {
        contentField.setText(model.name)
        spinnerView.setAdapter(typesAdapter)
        typesAdapter.dataSet = InputTypeItem.getSubset(listOf(8, 0, 7, 14, 20, 25, 30))
    }

}