package com.nativeboys.password.manager.presentation

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.github.leonardoxh.keystore.CipherStorage
import com.nativeboys.password.manager.BuildConfig
import com.nativeboys.password.manager.BuildConfig.DATABASE_BACKUP
import com.nativeboys.password.manager.data.local.AppDatabase
import com.nativeboys.password.manager.data.preferences.PreferencesManager
import com.nativeboys.password.manager.util.storage.FileUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.androidexception.roomdatabasebackupandrestore.Backup
import ir.androidexception.roomdatabasebackupandrestore.Restore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val database: AppDatabase,
    private val cipherStorage: CipherStorage,
    private val preferencesManager: PreferencesManager
): ViewModel() {

    var importEncryptionKey: String? = null
    var exportEncryptionKey: String? = null
    val backupDatabase = MutableLiveData<Int?>(null)

    val darkThemeEnabled: Boolean
        get() {
            return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        }

    fun enableDarkTheme(enabled: Boolean) = viewModelScope.launch(context = Dispatchers.IO) {
        preferencesManager.updateDarkTheme(enabled)
    }

    fun requestPermission(masterPassword: String) = liveData {
        emit(cipherStorage.decrypt(BuildConfig.USER_MASTER_PASSWORD) == masterPassword)
    }

    fun importDatabase(
        uri: Uri,
        callback: (Boolean, String) -> Unit
    ) {
        val encryptionKey = importEncryptionKey ?: return callback(false, "Path not found")
        val filePath = FileUtils.getPath(context, uri) ?: return callback(false, "Path not found")
        Restore.Init()
            .database(database)
            .backupFilePath(filePath)
            .secretKey(encryptionKey)
            .onWorkFinishListener { success, message ->
                callback(success, message)
            }
            .execute()
    }

    fun exportDatabase(
        uri: Uri,
        callback: (Boolean, String) -> Unit
    ) {
        val encryptionKey = exportEncryptionKey ?: return callback(false, "Path not found")
        val filePath = FileUtils.treeUriToFilePath(context, uri) ?: return callback(false, "Path not found")
        Backup.Init()
            .database(database)
            .path(filePath)
            .fileName(DATABASE_BACKUP)
            .secretKey(encryptionKey)
            .onWorkFinishListener { success, message ->
                callback(success, if (success) filePath else message)
            }
            .execute()
    }

}