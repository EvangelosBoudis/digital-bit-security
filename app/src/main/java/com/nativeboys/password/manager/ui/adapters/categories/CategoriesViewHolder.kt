package com.nativeboys.password.manager.ui.adapters.categories

import android.graphics.Color
import android.view.View
import android.widget.ImageView
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
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder
import com.zeustech.zeuskit.ui.swipeRevealLayout.SwipeRevealLayout
import com.zeustech.zeuskit.ui.swipeRevealLayout.ViewBinderHelper
import net.steamcrafted.materialiconlib.MaterialIconView

class CategoriesViewHolder(
    itemView: View,
    private val binder: ViewBinderHelper
) : RecyclerViewHolder<CategoryData>(itemView) {

    private val container: SwipeRevealLayout = itemView.findViewById(R.id.container)
    private val visibleView: ConstraintLayout = itemView.findViewById(R.id.visible_view)
    private val moreBtn: ImageView = itemView.findViewById(R.id.more_btn)
    private val deleteBtn: ImageView = itemView.findViewById(R.id.delete_btn)

    private val thumbnailBackgroundHolder = itemView.findViewById<View>(R.id.thumbnail_background_holder)
    private val thumbnailHolder = itemView.findViewById<MaterialIconView>(R.id.thumbnail_holder)
    private val nameField = itemView.findViewById<TextView>(R.id.name_field)

    init {
        moreBtn.setOnClickListener(this)
        visibleView.setOnClickListener(this)
        deleteBtn.setOnClickListener(this)
    }

    override fun bind(model: CategoryData) {

        binder.bind(container, model.id)
        container.setLockDrag(true)

        Glide.with(itemView.context)
            .load(R.drawable.cell_shape)
            .intoView(visibleView)

        nameField.text = model.name

        Glide
            .with(itemView.context)
            .load(ContextCompat.getDrawable(itemView.context, R.drawable.stroke_shape))
            .intoView(thumbnailBackgroundHolder)

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

        Glide.with(itemView.context)
            .load(R.drawable.menu_icon)
            .into(moreBtn)

        Glide.with(itemView.context)
            .load(R.drawable.ic_baseline_delete_24)
            .into(deleteBtn)

        moreBtn.visibility = if (model.defaultCategory) View.GONE else View.VISIBLE

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.more_btn -> {
                if (container.isOpened) container.close(true)
                else container.open(true)
            }
            R.id.visible_view -> {
                if (container.isOpened) container.close(true)
                else super.onClick(v)
            }
            else -> {
                super.onClick(v)
            }
        }
    }

}