package com.nativeboys.password.manager.ui.content.home.settings

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.databinding.FragmentSettingsBinding
import com.nativeboys.password.manager.presentation.SettingsViewModel
import com.nativeboys.password.manager.util.isPermissionGranted
import com.zeustech.zeuskit.ui.views.BottomBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings), View.OnClickListener {

    private val viewModel: SettingsViewModel by viewModels()

    private val chooseFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        lifecycleScope.launchWhenResumed {
            val uri = result.data?.data
            if (result.resultCode == RESULT_OK && uri != null) {
                viewModel.importDatabase(uri) { success, _ ->
                    BottomBar(
                        requireView() as ViewGroup,
                        R.layout.bottom_cell,
                        R.id.content_field,
                        getString(if (success) R.string.backup_imported else R.string.unsuccessful_import),
                        View.TEXT_ALIGNMENT_TEXT_END,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private val chooseDirectoryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        lifecycleScope.launchWhenResumed {
            val uri = result.data?.data
            if (result.resultCode == RESULT_OK && uri != null) {
                viewModel.exportDatabase(uri) { success, message ->
                    BottomBar(
                        requireView() as ViewGroup,
                        R.layout.bottom_cell,
                        R.id.content_field,
                        if (success) getString(R.string.backup_exported, message) else message,
                        View.TEXT_ALIGNMENT_TEXT_START,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private val readFilePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        lifecycleScope.launchWhenResumed {
            if (granted) chooseFile()
        }
    }

    private val writeFilePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        lifecycleScope.launchWhenResumed {
            if (granted) chooseDirectory()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val biometricManager = BiometricManager.from(requireContext())
        val supportsBiometrics = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        BiometricManager.BIOMETRIC_SUCCESS*/
        val binding = FragmentSettingsBinding.bind(view)
        binding.apply {
            importDbBtn.setOnClickListener(this@SettingsFragment)
            exportDbBtn.setOnClickListener(this@SettingsFragment)
            modeSwitch.isChecked = viewModel.darkThemeEnabled
            modeSwitch.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    delay(350)
                    viewModel.enableDarkTheme(isChecked)
                }
            }
        }
        viewModel.backupDatabase.observe(viewLifecycleOwner) { type ->
            if (type == null) return@observe
            when (type) {
                1 -> {
                    if (isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) chooseFile()
                    else readFilePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                2 -> {
                    if (isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) chooseDirectory()
                    else writeFilePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
            viewModel.backupDatabase.value = null
        }
    }

    override fun onClick(v: View?) {
        val view = v ?: return
        val type = if (view.id == R.id.import_db_btn) 1 else if (view.id == R.id.export_db_btn) 2 else return
        BackupBottomFragment
            .newInstance(type)
            .show(childFragmentManager, BackupBottomFragment::class.java.simpleName)
    }

    private fun chooseFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "text/plain"
        }
        chooseFileLauncher.launch(intent)
    }

    private fun chooseDirectory() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        chooseDirectoryLauncher.launch(intent)
    }

}