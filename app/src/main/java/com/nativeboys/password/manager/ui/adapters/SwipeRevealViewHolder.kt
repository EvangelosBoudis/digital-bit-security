package com.nativeboys.password.manager.ui.adapters

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.other.intoView
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder
import com.zeustech.zeuskit.ui.swipeRevealLayout.SwipeRevealLayout

open class SwipeRevealViewHolder<T>(itemView: View) : RecyclerViewHolder<T>(itemView) {

    protected val container: SwipeRevealLayout = itemView.findViewById(R.id.container)
    private val visibleView: ConstraintLayout = itemView.findViewById(R.id.visible_view)
    protected val moreBtn: ImageView = itemView.findViewById(R.id.more_btn)
    private val editBtn: ImageView = itemView.findViewById(R.id.edit_btn)
    private val deleteBtn: ImageView = itemView.findViewById(R.id.delete_btn)

    override fun bind(model: T) {

        Glide.with(itemView.context)
            .load(R.drawable.cell_shape)
            .intoView(visibleView)

        Glide.with(itemView.context)
            .load(R.drawable.menu_icon)
            .into(moreBtn)

        Glide.with(itemView.context)
            .load(R.drawable.edit_icon)
            .into(editBtn)

        Glide.with(itemView.context)
            .load(R.drawable.trash_can_icon)
            .into(deleteBtn)

        moreBtn.setOnClickListener(this)
        visibleView.setOnClickListener(this)
        editBtn.setOnClickListener(this)
        deleteBtn.setOnClickListener(this)

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