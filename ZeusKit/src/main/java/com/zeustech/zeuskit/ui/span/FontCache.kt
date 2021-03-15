package com.zeustech.zeuskit.ui.span

import android.content.Context
import android.graphics.Typeface
import android.util.SparseArray
import androidx.core.content.res.ResourcesCompat

object FontCache {

    private val fontCache = SparseArray<Typeface>()

    fun getTypeface(context: Context, fontName: Int): Typeface? {
        fontCache[fontName]?.let {
            return it
        } ?: run {
            val typeface = ResourcesCompat.getFont(context, fontName)
            typeface?.let {
                fontCache.put(fontName, it)
                return it
            }
        }
        return null
    }

}