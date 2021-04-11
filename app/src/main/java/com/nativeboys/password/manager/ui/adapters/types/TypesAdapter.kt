package com.nativeboys.password.manager.ui.adapters.types

import android.content.Context
import android.text.SpannableString
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.nativeboys.password.manager.R
import com.zeustech.zeuskit.ui.autocomplete.AutoCompleteAdapter
import com.zeustech.zeuskit.ui.autocomplete.DescriptionModel

class TypesAdapter(context: Context):
    AutoCompleteAdapter<DescriptionModel>(
        context,
        R.layout.spinner_child_cell,
        R.font.breeze_sans_medium,
        ContextCompat.getColor(context, R.color.colorAccent)
    )
{

    override fun onBindView(model: DescriptionModel?, text: SpannableString?, viewHolder: View) {
        viewHolder.findViewById<TextView>(R.id.description_field).text = text ?: model?.getText()
    }

}