package com.nativeboys.password.manager.ui.adapters.tags

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.other.TagModel
import com.nativeboys.password.manager.other.intoView
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class TagsViewHolder(itemView: View) : RecyclerViewHolder<TagModel>(itemView) {

    private val container = itemView.findViewById<ConstraintLayout>(R.id.container)
    private val descriptionField = itemView.findViewById<TextView>(R.id.description_field)
    private val removeBtn = itemView.findViewById<ImageView>(R.id.remove_btn)

    init {
        removeBtn.setOnClickListener(this)
    }

    override fun bind(model: TagModel) {
        descriptionField.text = model.name
        Glide
            .with(itemView.context)
            .load(R.drawable.ic_baseline_clear_24)
            .into(removeBtn)
        Glide
            .with(itemView.context)
            .load(R.drawable.tag_shape)
            .intoView(container)
    }

}