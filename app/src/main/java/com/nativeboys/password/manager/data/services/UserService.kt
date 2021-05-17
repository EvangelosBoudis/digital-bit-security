package com.nativeboys.password.manager.data.services

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface UserService {

    suspend fun initDatabase(context: Context)

    fun importDatabase(context: Context, uri: Uri, importEncryptionKey: String?, callback: (Boolean, String) -> Unit)

    fun exportDatabase(context: Context, uri: Uri, exportEncryptionKey: String?, callback: (Boolean, String) -> Unit)

    suspend fun isUserAlreadyRegistered(): Boolean

    suspend fun registerUser(masterPassword: String)

    suspend fun requestPermission(masterPassword: String): Boolean

    fun isDarkThemeEnabled(): Boolean

    fun observeDarkThemeMode(): Flow<Int>

    suspend fun enableDarkTheme(enabled: Boolean)

}