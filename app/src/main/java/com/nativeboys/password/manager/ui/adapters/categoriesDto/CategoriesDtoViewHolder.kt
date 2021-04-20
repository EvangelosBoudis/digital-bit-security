package com.nativeboys.password.manager.ui.adapters.categoriesDto

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryDto
import com.nativeboys.password.manager.util.intoView
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class CategoriesDtoViewHolder(itemView: View) : RecyclerViewHolder<CategoryDto>(itemView) {

    private val descriptionField = itemView.findViewById<TextView>(R.id.description_field)

    override fun bind(model: CategoryDto) {
        descriptionField.text = model.description
        Glide
            .with(itemView.context)
            .load(if (model.selected) R.drawable.selected_tag_shape else R.drawable.tag_shape)
            .intoView(descriptionField)
    }

}