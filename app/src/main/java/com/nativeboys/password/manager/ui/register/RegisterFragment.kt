package com.nativeboys.password.manager.ui.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.flexbox.FlexboxLayoutManager
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.databinding.FragmentRegisterBinding
import com.nativeboys.password.manager.presentation.RegisterViewModel
import com.nativeboys.password.manager.ui.adapters.masterPasswordRequirement.MasterPasswordRequirementsAdapter
import com.nativeboys.password.manager.util.togglePasswordTransformation
import com.nativeboys.password.manager.util.wrapCells
import com.zeustech.zeuskit.ui.other.KeyboardManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(
    R.layout.fragment_register
), TextWatcher {

    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var navController: NavController
    private val adapter = MasterPasswordRequirementsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewModel.isUserAlreadyRegistered().observe(viewLifecycleOwner) { registered ->
            if (registered) {
                navController.navigate(R.id.action_register_to_login)
            }
        }
        val binding = FragmentRegisterBinding.bind(view)
        binding.apply {
            val layoutManager = FlexboxLayoutManager(requireContext())
            layoutManager.wrapCells()
            regularExpressionsRecyclerView.layoutManager = layoutManager
            regularExpressionsRecyclerView.adapter = adapter
            passwordField.addTextChangedListener(this@RegisterFragment)
            passwordField.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) KeyboardManager().hideKeyboard(v)
                return@setOnEditorActionListener true
            }
            showHideBtn.setOnClickListener {
                passwordField.togglePasswordTransformation()
            }
            registerBtn.setOnClickListener {
                val masterPassword = passwordField.text?.toString() ?: ""
                viewModel.register(masterPassword).observe(viewLifecycleOwner) {
                    navController.navigate(R.id.action_register_to_content)
                }
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