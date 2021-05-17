package com.nativeboys.password.manager.presentation

import android.content.Context
import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nativeboys.password.manager.data.services.UserService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val userService: UserService
): ViewModel() {

    var importEncryptionKey: String? = null
    var exportEncryptionKey: String? = null
    val backupDatabase = MutableLiveData<Int?>(null)

    val darkThemeEnabled: Boolean
        get() {
            return userService.isDarkThemeEnabled()
        }

    fun enableDarkTheme(enabled: Boolean) = viewModelScope.launch(context = Dispatchers.IO) {
        userService.enableDarkTheme(enabled)
    }

    fun requestPermission(masterPassword: String) = liveData {
        emit(userService.requestPermission(masterPassword))
    }

    fun importDatabase(uri: Uri, callback: (Boolean, String) -> Unit) {
        userService.importDatabase(context, uri, importEncryptionKey, callback)
    }

    fun exportDatabase(uri: Uri, callback: (Boolean, String) -> Unit) {
        userService.exportDatabase(context, uri, exportEncryptionKey, callback)
    }

}