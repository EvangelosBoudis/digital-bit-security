package com.nativeboys.password.manager.data.services

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate
import com.github.leonardoxh.keystore.CipherStorage
import com.nativeboys.password.manager.BuildConfig
import com.nativeboys.password.manager.data.storage.AppDatabase
import com.nativeboys.password.manager.data.storage.CategoryDao
import com.nativeboys.password.manager.data.preferences.PreferencesManager
import com.nativeboys.uikit.storage.FileUtils
import ir.androidexception.roomdatabasebackupandrestore.Backup
import ir.androidexception.roomdatabasebackupandrestore.Restore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val db: AppDatabase,
    private val cipherStorage: CipherStorage,
    private val preferencesManager: PreferencesManager,
    private val categoryDao: CategoryDao
): UserService {

    override suspend fun initDatabase(context: Context) {
        if (categoryDao.countAll() != 0) return
        FileUtils.copyFileFromAssetsToMainDirectory(
            context,
            "database/database_init.json",
            BuildConfig.DATABASE_BACKUP
        )
        Restore.Init()
            .database(db)
            .backupFilePath(context.externalCacheDir?.path + File.separator + BuildConfig.DATABASE_BACKUP)
            .execute()
    }

    override fun importDatabase(
        context: Context,
        uri: Uri,
        importEncryptionKey: String?,
        callback: (Boolean, String) -> Unit
    ) {
        val encryptionKey = importEncryptionKey ?: return callback(false, "Path not found")
        val filePath = FileUtils.getPath(context, uri) ?: return callback(false, "Path not found")
        Restore.Init()
            .database(db)
            .backupFilePath(filePath)
            .secretKey(encryptionKey)
            .onWorkFinishListener { success, message ->
                callback(success, message)
            }
            .execute()
    }

    override fun exportDatabase(
        context: Context,
        uri: Uri,
        exportEncryptionKey: String?,
        callback: (Boolean, String) -> Unit
    ) {
        val encryptionKey = exportEncryptionKey ?: return callback(false, "Path not found")
        val filePath = FileUtils.treeUriToFilePath(context, uri) ?: return callback(false, "Path not found")
        Backup.Init()
            .database(db)
            .path(filePath)
            .fileName(BuildConfig.DATABASE_BACKUP)
            .secretKey(encryptionKey)
            .onWorkFinishListener { success, message ->
                callback(success, if (success) filePath else message)
            }
            .execute()
    }

    override suspend fun isUserAlreadyRegistered(): Boolean {
        return cipherStorage.containsAlias(BuildConfig.USER_MASTER_PASSWORD)
    }

    override suspend fun registerUser(masterPassword: String) {
        cipherStorage.encrypt(BuildConfig.USER_MASTER_PASSWORD, masterPassword)
    }

    override suspend fun requestPermission(masterPassword: String): Boolean {
        return cipherStorage.decrypt(BuildConfig.USER_MASTER_PASSWORD) == masterPassword
    }

    override fun isDarkThemeEnabled(): Boolean {
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }

    override fun observeDarkThemeMode(): Flow<Int> {
        return preferencesManager
            .observeDarkTheme()
            .map { enabled ->
                if (enabled) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            }
    }

    override suspend fun enableDarkTheme(enabled: Boolean) {
        preferencesManager.updateDarkTheme(enabled)
    }

}