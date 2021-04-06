package com.nativeboys.password.manager.ui.adapters.types

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.other.InputTypeItem
import com.nativeboys.password.manager.other.intoView
import com.zeustech.zeuskit.ui.spinner.SpinnerAdapter

class TypesSpinnerAdapter(context: Context):
    SpinnerAdapter<InputTypeItem>(
        context,
        R.layout.spinner_parent_cell,
        R.layout.spinner_child_cell
    ) {

    override fun onBindParent(model: InputTypeItem?, viewHolder: View) {
        Glide
            .with(viewHolder.context)
            .load(R.drawable.ic_baseline_arrow_drop_down_24)
            .into(viewHolder.findViewById(R.id.trailing_icon))
        Glide
            .with(viewHolder.context)
            .load(R.drawable.spinner_shape)
            .intoView(viewHolder.findViewById<ConstraintLayout>(R.id.container))
        setUpView(model, viewHolder)
    }

    override fun onBindChild(model: InputTypeItem?, viewHolder: View) = setUpView(model, viewHolder)

    private fun setUpView(model: InputTypeItem?, viewHolder: View) {
        viewHolder.findViewById<TextView>(R.id.description_field).text = model?.description
    }

}