package com.nativeboys.password.manager.ui.adapters.categories

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.util.intoMaterialIcon
import com.nativeboys.password.manager.util.intoView
import com.nativeboys.password.manager.util.materialIconCodeToDrawable
import com.nativeboys.password.manager.ui.adapters.SwipeRevealViewHolder
import com.zeustech.zeuskit.ui.swipeRevealLayout.ViewBinderHelper
import net.steamcrafted.materialiconlib.MaterialIconView

class CategoriesViewHolder(
    itemView: View,
    private val binder: ViewBinderHelper
) : SwipeRevealViewHolder<CategoryData>(itemView) {

    private val thumbnailBackgroundHolder = itemView.findViewById<View>(R.id.thumbnail_background_holder)
    private val thumbnailHolder = itemView.findViewById<MaterialIconView>(R.id.thumbnail_holder)
    private val nameField = itemView.findViewById<TextView>(R.id.name_field)

    override fun bind(model: CategoryData) {
        super.bind(model)
        binder.bind(container, model.id)
        container.setLockDrag(true)

        nameField.text = model.name
        moreBtn.visibility = if (model.adminCategory) View.INVISIBLE else View.VISIBLE

        val draw = materialIconCodeToDrawable(itemView.context, model.thumbnailCode)
        if (draw != null) {
            Glide.with(itemView.context)
                .load(draw)
                .transition(DrawableTransitionOptions().crossFade())
                .intoMaterialIcon(thumbnailHolder)
        } else {
            thumbnailHolder.setImageDrawable(null)
        }

        Glide
            .with(itemView.context)
            .load(R.drawable.stroke_shape)
            .intoView(thumbnailBackgroundHolder)
    }

}