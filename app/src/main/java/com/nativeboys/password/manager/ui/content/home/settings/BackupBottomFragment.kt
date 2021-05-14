package com.nativeboys.password.manager.ui.content.home.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.databinding.FragmentBackupBottomBinding
import com.nativeboys.password.manager.presentation.SettingsViewModel
import com.nativeboys.password.manager.util.togglePasswordTransformation
import com.nativeboys.uikit.fragments.BottomFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BackupBottomFragment : BottomFragment(
    R.layout.fragment_backup_bottom,
    FRAGMENT_WRAP_CONTENT,
    true
) {

    private val viewModel: SettingsViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private var type: Int ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            type = bundle.getInt(BACKUP_TYPE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBackupBottomBinding.bind(view)
        binding.apply {
            headerHeadline.setText(if (type == 1) R.string.import_backup else R.string.export_backup)
            submitBtn.setText(if (type == 1) R.string.import_ else R.string.export)
            submitBtn.setOnClickListener {
                val masterPassword = masterPasswordField.text?.toString() ?: ""
                val encryptionKey = encryptionKeyField.text?.toString() ?: ""
                if (masterPassword.isNotBlank() && encryptionKey.isNotBlank()) {
                    viewModel.requestPermission(masterPassword).observe(viewLifecycleOwner) { granted ->
                        if (granted) {
                            if (type == 1) viewModel.importEncryptionKey = encryptionKey
                            else if (type == 2) viewModel.exportEncryptionKey = encryptionKey
                            viewModel.backupDatabase.value = type
                            dismiss()
                        } else {
                            wrongMasterPasswordField.visibility = View.VISIBLE
                            lifecycleScope.launch {
                                delay(2000)
                                wrongMasterPasswordField.visibility = View.INVISIBLE
                            }
                        }
                    }
                }
            }
            clearEncryptionKeyBtn.setOnClickListener {
                encryptionKeyField.togglePasswordTransformation()
            }
            clearMasterPasswordBtn.setOnClickListener {
                masterPasswordField.togglePasswordTransformation()
            }
        }
    }

    companion object {

        private const val BACKUP_TYPE = "backup_type"

        @JvmStatic
        fun newInstance(type: Int) =
            BackupBottomFragment().apply {
                arguments = Bundle().apply {
                    putInt(BACKUP_TYPE, type)
                }
            }

    }

}