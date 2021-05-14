package com.nativeboys.password.manager.ui.adapters.masterPasswordRequirement

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.UIMasterPasswordRequirement
import com.nativeboys.password.manager.util.intoView
import com.nativeboys.uikit.rv.RecyclerViewHolder

class MasterPasswordRequirementsViewHolder(
    itemView: View
) : RecyclerViewHolder<UIMasterPasswordRequirement>(itemView) {

    private val descriptionField = itemView.findViewById<TextView>(R.id.description_field)

    override fun bind(model: UIMasterPasswordRequirement) {
        descriptionField.setText(model.descriptionResId)
        Glide
            .with(itemView.context)
            .load(ContextCompat.getDrawable(itemView.context, if (model.accepted) R.drawable.selected_category_shape else R.drawable.tag_shape))
            .intoView(descriptionField)
    }

}