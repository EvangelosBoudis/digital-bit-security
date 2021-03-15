package com.zeustech.zeuskit.tools.other

import android.content.Context
import android.os.Build
import java.util.*

class LocalizationSystem {

    fun updateLanguage(context: Context, lanCode: String) {
        val res = context.resources
        // Change locale settings in the app.
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(Locale(lanCode)) // API 17+ only.
        res.updateConfiguration(conf, dm)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //context.createConfigurationContext(conf)
        } else {
            res.updateConfiguration(conf, dm)
        }
    }

}