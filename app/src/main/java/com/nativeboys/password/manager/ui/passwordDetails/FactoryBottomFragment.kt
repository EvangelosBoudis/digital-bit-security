package com.nativeboys.password.manager.ui.passwordDetails

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.databinding.FragmentFactoryBottomBinding
import com.zeustech.zeuskit.ui.fragments.BottomFragment

class FactoryBottomFragment : BottomFragment(R.layout.fragment_factory_bottom, R.style.CustomBottomSheetDialogTheme),
    View.OnClickListener {

    private var headerRes: Int? = null
    private var fieldRes: Int? = null
    private var binding: FragmentFactoryBottomBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            headerRes = it.getInt(HEADER_RES)
            fieldRes = it.getInt(FIELD_RES)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFactoryBottomBinding.bind(view)
        headerRes?.let {
            binding?.headerHeadline?.setText(it)
        }
        fieldRes?.let {
            binding?.fieldHeadline?.setText(it)
        }
        binding?.clearFieldBtn?.setOnClickListener(this)
        binding?.submitBtn?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        when (view.id) {
            R.id.clear_field_btn -> {
                binding?.field?.text = null
            }
            R.id.submit_btn -> {
                // TODO: implement
            }
        }
    }

    companion object {

        private const val HEADER_RES = "headerRes"
        private const val FIELD_RES = "fieldRes"

        @JvmStatic
        fun newInstance(@StringRes headerRes: Int, @StringRes fieldRes: Int) =
            FactoryBottomFragment().apply {
                arguments = Bundle().apply {
                    putInt(HEADER_RES, headerRes)
                    putInt(FIELD_RES, fieldRes)
                }
            }

        @JvmStatic
        fun showFragment(parent: Fragment, @StringRes headerRes: Int, @StringRes fieldRes: Int) {
                newInstance(headerRes, fieldRes)
                    .show(parent.childFragmentManager, FactoryBottomFragment::class.java.simpleName)
        }

    }

}

// https://guides.codepath.com/android/handling-scrolls-with-coordinatorlayout