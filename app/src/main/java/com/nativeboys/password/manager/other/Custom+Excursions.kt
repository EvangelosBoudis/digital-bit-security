package com.nativeboys.password.manager.other

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.flexbox.*
import com.google.android.material.transition.MaterialSharedAxis
import com.nativeboys.password.manager.R
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder
import net.steamcrafted.materialiconlib.MaterialIconView

fun FlexboxLayoutManager.wrapCells() {
    this.flexWrap = FlexWrap.WRAP
    this.flexDirection = FlexDirection.ROW
    this.justifyContent = JustifyContent.FLEX_START
    this.alignItems = AlignItems.FLEX_START
}

fun EditText.toggleTransformationMethod() {
    val hidden = this.transformationMethod is PasswordTransformationMethod
    val updatedMethod: TransformationMethod = if (hidden) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
    this.transformationMethod = updatedMethod
    this.setSelection(this.length())
}

fun EditText.setTransformationMethodAsHidden(hidden: Boolean) {
    this.transformationMethod = if (hidden) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
}

fun materialIconCodeToDrawable(
    context: Context,
    materialIconCode: String,
    @ColorInt color: Int = Color.WHITE
): Drawable? {
    return try {
        MaterialDrawableBuilder.IconValue.valueOf(materialIconCode)
    } catch (e: IllegalArgumentException) {
        null
    }?.let { icon ->
        MaterialDrawableBuilder.with(context) // provide a context
            .setIcon(icon) // provide an icon
            .setColor(color) // set the icon color
            .setToActionbarSize() // set the icon size
            .build() // Finally call build
    }
}

fun ImageView.setMaterialIcon(
    materialIconCode: String,
    @ColorInt color: Int = Color.WHITE
) = materialIconCodeToDrawable(context, materialIconCode, color)?.let { setImageDrawable(it) }

fun RequestBuilder<Drawable>.intoView(view: View) {
    into(object : CustomTarget<Drawable>() {
        override fun onResourceReady(
            resource: Drawable,
            transition: Transition<in Drawable>?
        ) {
            view.background = resource
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    })
}

fun RequestBuilder<Drawable>.intoMaterialIcon(view: MaterialIconView) {
    into(object : CustomTarget<Drawable>() {
        override fun onResourceReady(
            resource: Drawable,
            transition: Transition<in Drawable>?
        ) {
            view.setImageDrawable(resource)
        }

        override fun onLoadCleared(placeholder: Drawable?) {
            view.setImageDrawable(null)
        }
    })
}

fun copyToClipboard(context: Context, label: String, text: String) {
    val clipboard = ContextCompat.getSystemService(context, ClipboardManager::class.java)
    val clip = ClipData.newPlainText(label, text)
    clipboard?.setPrimaryClip(clip)
}

fun Fragment.applyZTransition() {
    enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
        duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
    }
    returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
        duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
    }
}

fun Fragment.parentNavController() =
    parentFragment?.parentFragment?.view?.let {
        Navigation.findNavController(it)
    }