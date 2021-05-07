package com.nativeboys.password.manager.presentation

import android.content.Context
import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.nativeboys.password.manager.BuildConfig.DATABASE_BACKUP
import com.nativeboys.password.manager.data.local.AppDatabase
import com.nativeboys.password.manager.util.storage.FileUtils
import ir.androidexception.roomdatabasebackupandrestore.Backup
import ir.androidexception.roomdatabasebackupandrestore.Restore

class SettingsViewModel @ViewModelInject constructor(
    private val database: AppDatabase
): ViewModel() {

    fun importDatabase(
        context: Context,
        uri: Uri,
        encryptionKey: String,
        callback: (Boolean, String) -> Unit
    ) {
        // val exportPath = context.externalCacheDir?.path
        // exportPath + File.separator + DATABASE_BACKUP
        FileUtils.getPath(context, uri)?.let { path ->
            Restore.Init()
                .database(database)
                .backupFilePath(path)
                .secretKey(encryptionKey)
                .onWorkFinishListener { success, message ->
                    callback(success, message)
                }
                .execute()
        } ?: kotlin.run {
            callback(false, "Path not found")
        }
    }

    fun exportDatabase(
        context: Context,
        uri: Uri,
        encryptionKey: String,
        callback: (Boolean, String) -> Unit
    )  {
        // val exportPath = context.externalCacheDir?.path ?:
        // return callback(Pair(false, "Path not found"))
        FileUtils.getPath(context, uri)?.let { path ->
            Backup.Init()
                .database(database)
                .path(path)
                .fileName(DATABASE_BACKUP)
                .secretKey(encryptionKey)
                .onWorkFinishListener { success, message ->
                    callback(success, if (success) path else message)
                }
                .execute()
        } ?: kotlin.run {
            callback(false, "Path not found")
        }
    }

}