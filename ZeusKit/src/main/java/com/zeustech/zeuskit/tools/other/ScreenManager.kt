package com.zeustech.zeuskit.tools.other

import android.content.Context
import android.content.res.Resources
import com.zeustech.zeuskit.R

object ScreenManager {

    private const val PHONE = "phone"

    val width: Int
        get() = Resources.getSystem().displayMetrics.widthPixels
    val height: Int
        get() = Resources.getSystem().displayMetrics.heightPixels

    fun isPhone(context: Context): Boolean {
        return context.getString(R.string.screen_type) == PHONE
    }

}