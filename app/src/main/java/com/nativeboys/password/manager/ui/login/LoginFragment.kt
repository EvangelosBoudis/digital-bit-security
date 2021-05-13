package com.nativeboys.password.manager.ui.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.databinding.FragmentLoginBinding
import com.nativeboys.password.manager.presentation.LoginViewModel
import com.nativeboys.password.manager.util.togglePasswordTransformation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var navController: NavController
    private var binding: FragmentLoginBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentLoginBinding.bind(view)
        binding?.apply {
            passwordField.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_GO) requestPermission()
                return@setOnEditorActionListener true
            }
            showHideBtn.setOnClickListener {
                passwordField.togglePasswordTransformation()
            }
            submitBtn.setOnClickListener {
                requestPermission()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun requestPermission() {
        binding?.apply {
            val masterPassword = passwordField.text?.toString() ?: ""
            viewModel.requestPermission(masterPassword).observe(viewLifecycleOwner) { granted ->
                if (granted) {
                    when (viewModel.destination) {
                        2 -> { // ItemOverviewFragment
                            val itemId = viewModel.data ?: return@observe
                            navController.navigate(LoginFragmentDirections.actionLoginToItemOverview(itemId))
                        }
                        3 -> { // ItemConstructorFragment
                            val itemId = viewModel.data ?: return@observe
                            navController.navigate(LoginFragmentDirections.actionLoginToItemConstructor(itemId, null))
                        }
                        else -> { // ContentFragment
                            navController.navigate(R.id.action_login_to_content)
                        }
                    }
                } else {
                    wrongPassField.visibility = View.VISIBLE
                    lifecycleScope.launch {
                        delay(2000)
                        wrongPassField.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

}