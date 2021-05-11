package com.nativeboys.password.manager.ui.adapters.tags

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.TagDto
import com.nativeboys.password.manager.util.intoView
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class TagsViewHolder(itemView: View) : RecyclerViewHolder<TagDto>(itemView) {

    private val descriptionField = itemView.findViewById<TextView>(R.id.description_field)

    override fun bind(model: TagDto) {
        descriptionField.text = model.name
        Glide
            .with(itemView.context)
            .load(ContextCompat.getDrawable(itemView.context, R.drawable.tag_shape)) // Work around for Glide - Theme support https://github.com/bumptech/glide/issues/3778
            .intoView(descriptionField)
    }

}