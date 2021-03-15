package com.zeustech.zeuskit.ui.fragments

import android.graphics.Point
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.zeustech.zeuskit.R

open class FragmentDialogScreen(
    @LayoutRes private val contentLayoutId: Int,
    private val dStyle: Int = STYLE_NORMAL,
    private val dTheme: Int = R.style.FullScreenDialogTheme,
    var fullScreenSize: Boolean = true
) : DialogFragment() {

    var wrapContentHeight = false
    var wrapContentWidth = false
    var widthPercentage = -1.0
    var heightPercentage = -1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(dStyle, dTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(contentLayoutId, container, false)
    }

    override fun onResume() {
        applyScreenSize()
        super.onResume() // Call super onResume after sizing
    }

    private fun applyScreenSize() {
        dialog?.let { dialog ->
            dialog.window?.let { window ->
                // Store access variables for window and blank point
                val size = Point()
                // Store dimensions of the screen in `size`
                val display = window.windowManager.defaultDisplay
                display.getSize(size)
                // Set the width of the dialog proportional to 75% of the screen width
                val width: Int = when {
                    fullScreenSize -> {
                        WindowManager.LayoutParams.MATCH_PARENT
                    }
                    wrapContentWidth -> {
                        WindowManager.LayoutParams.WRAP_CONTENT
                    }
                    else -> {
                        (size.x * if (widthPercentage != -1.0) widthPercentage else 0.85).toInt()
                    }
                }
                val height: Int = when {
                    fullScreenSize -> {
                        WindowManager.LayoutParams.MATCH_PARENT
                    }
                    wrapContentHeight -> {
                        WindowManager.LayoutParams.WRAP_CONTENT
                    }
                    else -> {
                        (size.y * if (heightPercentage != -1.0) heightPercentage else 0.60).toInt()
                    }
                }
                window.setLayout(width, height)
                window.setGravity(Gravity.CENTER)
            }
        }
    }
}