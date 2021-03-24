package com.nativeboys.password.manager.ui.adapters.types

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.InputTypeItem
import com.zeustech.zeuskit.ui.spinner.SpinnerAdapter

class TypesAdapter(context: Context):
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
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    viewHolder.findViewById<ConstraintLayout>(R.id.container).background = resource
                }
                override fun onLoadCleared(placeholder: Drawable?) { }
            })
        setUpView(model, viewHolder)
    }

    override fun onBindChild(model: InputTypeItem?, viewHolder: View) = setUpView(model, viewHolder)

    private fun setUpView(model: InputTypeItem?, viewHolder: View) {
        viewHolder.findViewById<TextView>(R.id.description_field).text = model?.description
    }

}