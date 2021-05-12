package com.nativeboys.password.manager.ui.passwordConstructor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexboxLayoutManager
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.databinding.FragmentPasswordConstructorBinding
import com.nativeboys.password.manager.presentation.PasswordConstructorViewModel
import com.nativeboys.password.manager.ui.adapters.masterPasswordRequirement.MasterPasswordRequirementsAdapter
import com.nativeboys.password.manager.util.wrapCells
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordConstructorFragment : Fragment(
    R.layout.fragment_password_constructor
), TextWatcher {

    private val viewModel: PasswordConstructorViewModel by viewModels()

    private val adapter = MasterPasswordRequirementsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPasswordConstructorBinding.bind(view)
        binding.apply {
            val layoutManager = FlexboxLayoutManager(requireContext())
            layoutManager.wrapCells()
            regularExpressionsRecyclerView.layoutManager = layoutManager
            regularExpressionsRecyclerView.adapter = adapter
            passwordField.addTextChangedListener(this@PasswordConstructorFragment)
            registerBtn.setOnClickListener {
                val masterPassword = passwordField.text?.toString() ?: ""
                viewModel.register(masterPassword)
            }
        }
        viewModel.masterPasswordRequirements.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.registerBtnEnabled.observe(viewLifecycleOwner) {
            binding.registerBtn.isEnabled = it
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

    override fun afterTextChanged(s: Editable?) {
        viewModel.password.value = s?.toString() ?: ""
    }

}