package com.nativeboys.password.manager.ui.adapters.categories

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.ui.adapters.SharedCategoriesViewHolder
import com.zeustech.zeuskit.ui.other.vectorTintResColor

class CategoriesViewHolder(itemView: View) : SharedCategoriesViewHolder(itemView) {

    private val trailingBtn = itemView.findViewById<ImageView>(R.id.trailing_btn)

    init {
        trailingBtn.setOnClickListener(this)
    }

    override fun bind(model: CategoryData) {
        super.bind(model)
        Glide
            .with(itemView.context)
            .load(if (model.defaultCategory) R.drawable.ic_baseline_lock_24 else R.drawable.ic_baseline_delete_24)
            .into(trailingBtn)
        trailingBtn.vectorTintResColor(if (model.defaultCategory) R.color.lightGray else R.color.red)
    }

}