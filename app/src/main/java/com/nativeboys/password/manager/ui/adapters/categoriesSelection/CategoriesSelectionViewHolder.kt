package com.nativeboys.password.manager.ui.adapters.categoriesSelection

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryDto
import com.nativeboys.password.manager.util.intoView
import com.nativeboys.uikit.rv.RecyclerViewHolder

class CategoriesSelectionViewHolder(itemView: View) : RecyclerViewHolder<CategoryDto>(itemView) {

    private val descriptionField = itemView.findViewById<TextView>(R.id.description_field)

    override fun bind(model: CategoryDto) {
        descriptionField.text = model.description
        // Work around for Glide - Theme support https://github.com/bumptech/glide/issues/3778
        Glide
            .with(itemView.context)
            .load(ContextCompat.getDrawable(itemView.context, if (model.selected) R.drawable.selected_category_shape else R.drawable.tag_shape))
            .intoView(descriptionField)
    }

}