package com.nativeboys.password.manager.ui.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.nativeboys.password.manager.R

interface ConfirmationDialogListener {
    fun onClick(dialogFragment: DialogFragment, view: View)
}

class ConfirmationFragment : DialogFragment(), View.OnClickListener {

    private var layoutRes: Int? = null
    var confirmationDialogListener: ConfirmationDialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutRes = it.getInt(ARG_LAYOUT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes ?: R.layout.fragment_confirmation, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.leading_btn).setOnClickListener(this)
        view.findViewById<Button>(R.id.trailing_btn).setOnClickListener(this)
    }

    override fun onDetach() {
        super.onDetach()
        confirmationDialogListener = null
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        confirmationDialogListener?.onClick(this, view)
    }

    companion object {

        private const val ARG_LAYOUT = "layout_param"

        @JvmStatic
        fun newInstance(@LayoutRes layoutRes: Int) =
            ConfirmationFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_LAYOUT, layoutRes)
                }
            }
    }

}