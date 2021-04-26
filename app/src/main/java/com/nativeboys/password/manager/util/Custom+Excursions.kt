package com.nativeboys.password.manager.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Looper
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.Navigation
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.flexbox.*
import com.google.android.material.transition.MaterialSharedAxis
import com.nativeboys.password.manager.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder
import net.steamcrafted.materialiconlib.MaterialIconView
import java.util.*

fun String.toComparable() = toLowerCase(Locale.ROOT).trim { it <= ' ' }

inline fun <T> List<T>.contains(predicate: (T) -> Boolean) = indexOfFirst(predicate) != -1

fun <T> SavedStateHandle.safeSet(tag: String, value: T, scope: CoroutineScope) {
    if (Thread.currentThread() == Looper.getMainLooper().thread) {
        this[tag] = value
    } else {
        scope.launch(context = Dispatchers.Main) {
            this@safeSet[tag] = value
        }
    }
}

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

fun EditText.setTextAndFixCursor(text: String?) {
    setText(text)
    setSelection(text?.length ?: 0)
}

fun materialIconCodeToDrawable(
    context: Context,
    iconCode: String,
    @ColorInt iconColor: Int = Color.WHITE,
    iconSize: Int = MaterialDrawableBuilder.ANDROID_ACTIONBAR_ICON_SIZE_DP
): Drawable? {
    return try {
        MaterialDrawableBuilder.IconValue.valueOf(iconCode)
    } catch (e: IllegalArgumentException) {
        null
    }?.let { icon ->
        MaterialDrawableBuilder.with(context) // provide a context
            .setIcon(icon) // provide an icon
            .setColor(iconColor) // set the icon color
            .setSizeDp(iconSize) // set the icon size
            .build() // Finally call build
    }
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

fun RadioGroup.checkedRadioButtonIndex() = findViewById<RadioButton>(checkedRadioButtonId)?.let { indexOfChild(it) }

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

//Regex().matches("")
//https://stackoverflow.com/questions/5142103/regex-to-validate-password-strength
//https://github.com/zeustechgr/doo-Mobile-And/blob/master/app/src/main/java/com/zeustech/doo/ui/book/checkout/CheckoutFragment.kt