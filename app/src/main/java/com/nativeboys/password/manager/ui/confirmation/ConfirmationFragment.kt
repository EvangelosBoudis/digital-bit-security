package com.nativeboys.password.manager.ui.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.nativeboys.password.manager.R

interface ConfirmationDialogListener {
    fun onClick(dialogFragment: DialogFragment, view: View)
}

class ConfirmationFragment : DialogFragment(), View.OnClickListener {

    private var layoutRes: Int? = null
    private var headline: String? = null
    private var description: String? = null

    var confirmationDialogListener: ConfirmationDialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutRes = it.getInt(ARG_LAYOUT)
            headline = it.getString(ARG_HEADLINE)
            description = it.getString(ARG_DESCRIPTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes ?: R.layout.dialog_confirmation, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        headline?.let {
            view.findViewById<TextView>(R.id.headline_field)?.text = headline
        }
        description?.let {
            view.findViewById<TextView>(R.id.description_field)?.text = it
        }
        view.findViewById<Button>(R.id.leading_btn)?.setOnClickListener(this)
        view.findViewById<Button>(R.id.trailing_btn)?.setOnClickListener(this)
    }

    override fun onDetach() {
        super.onDetach()
        confirmationDialogListener = null
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        confirmationDialogListener?.onClick(this, view) ?: dismiss()
    }

    companion object {

        private const val ARG_LAYOUT = "layout_param"
        private const val ARG_HEADLINE = "headline_param"
        private const val ARG_DESCRIPTION = "description_param"

        @JvmStatic
        fun newInstance(@LayoutRes layoutRes: Int, headline: String, description: String?) =
            ConfirmationFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_LAYOUT, layoutRes)
                    putString(ARG_HEADLINE, headline)
                    putString(ARG_DESCRIPTION, description)
                }
            }
    }

}