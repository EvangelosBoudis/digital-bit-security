package com.nativeboys.uikit.fragments

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nativeboys.uikit.R

open class BottomFragment(
    @LayoutRes private val contentLayoutId: Int,
    private val heightPercentage: Float,
    private val cancelable: Boolean
) : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            (dialogInterface as? BottomSheetDialog)?.let { bottomDialogSheet ->
                if (heightPercentage != FRAGMENT_WRAP_CONTENT) {
                    bottomDialogSheet.setPercentageHeight(heightPercentage)
                }
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(contentLayoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = cancelable
    }

    companion object {

        const val FRAGMENT_WRAP_CONTENT = -1f

    }

}

fun BottomSheetDialog.setPercentageHeight(heightPercentage: Float) {
    val bottomSheet = findViewById<View>(R.id.design_bottom_sheet) as? FrameLayout ?: return
    val layoutParams = bottomSheet.layoutParams
    val windowHeight = Resources.getSystem().displayMetrics.heightPixels
    layoutParams?.height = ((windowHeight * heightPercentage) / 100).toInt()
    bottomSheet.layoutParams = layoutParams
    BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
}