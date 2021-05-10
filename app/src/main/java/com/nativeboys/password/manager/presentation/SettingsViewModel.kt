package com.nativeboys.password.manager.presentation

import android.content.Context
import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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
    private val preferencesManager: PreferencesManager
): ViewModel() {

    val darkThemeEnabled = preferencesManager.observeDarkTheme().asLiveData()

    fun enableDarkTheme(enabled: Boolean) = viewModelScope.launch(context = Dispatchers.IO) {
        preferencesManager.updateDarkTheme(enabled)
    }

    fun importDatabase(
        uri: Uri,
        encryptionKey: String,
        callback: (Boolean, String) -> Unit
    ) {
        FileUtils.getPath(context, uri)?.let { path ->
            Restore.Init()
                .database(database)
                .backupFilePath(path)
                // .secretKey(encryptionKey)
                .onWorkFinishListener { success, message ->
                    callback(success, message)
                }
                .execute()
        } ?: kotlin.run {
            callback(false, "Path not found")
        }
    }

    fun exportDatabase(
        uri: Uri,
        encryptionKey: String,
        callback: (Boolean, String) -> Unit
    )  {
        FileUtils.treeUriToFilePath(context, uri)?.let { path ->
            Backup.Init()
                .database(database)
                .path(path)
                .fileName(DATABASE_BACKUP)
                //.secretKey(encryptionKey)
                .onWorkFinishListener { success, message ->
                    callback(success, if (success) path else message)
                }
                .execute()
        } ?: kotlin.run {
            callback(false, "Path not found")
        }
    }

}