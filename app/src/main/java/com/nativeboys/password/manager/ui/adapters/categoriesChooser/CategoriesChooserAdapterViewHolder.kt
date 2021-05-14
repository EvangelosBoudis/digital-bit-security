package com.nativeboys.password.manager.ui.adapters.categoriesChooser

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.util.intoMaterialIcon
import com.nativeboys.password.manager.util.intoView
import com.nativeboys.password.manager.util.materialIconCodeToDrawable
import com.nativeboys.uikit.rv.RecyclerViewHolder
import net.steamcrafted.materialiconlib.MaterialIconView

class CategoriesChooserAdapterViewHolder(itemView: View) : RecyclerViewHolder<CategoryData>(itemView) {

    private val container = itemView.findViewById<ConstraintLayout>(R.id.container)
    private val thumbnailBackgroundHolder = itemView.findViewById<View>(R.id.thumbnail_background_holder)
    private val thumbnailHolder = itemView.findViewById<MaterialIconView>(R.id.thumbnail_holder)
    private val nameField = itemView.findViewById<TextView>(R.id.name_field)

    override fun bind(model: CategoryData) {

        Glide.with(itemView.context)
            .load(R.drawable.cell_shape)
            .intoView(container)

        nameField.text = model.name

        val draw = materialIconCodeToDrawable(
            itemView.context,
            model.thumbnailCode,
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) Color.WHITE else Color.BLACK
        )
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
            .load(ContextCompat.getDrawable(itemView.context, R.drawable.stroke_shape))
            .intoView(thumbnailBackgroundHolder)
    }

}