package com.nativeboys.password.manager.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.github.leonardoxh.keystore.CipherStorage
import com.nativeboys.password.manager.BuildConfig.USER_MASTER_PASSWORD
import com.nativeboys.password.manager.data.preferences.PreferencesManager

class LoginViewModel @ViewModelInject constructor(
    private val cipherStorage: CipherStorage,
    private val preferencesManager: PreferencesManager,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    val destination: Int? = state.get<Int>("DESTINATION")
    val data: String? = state.get<String>("DATA")

    fun requestPermission(masterPassword: String) = liveData {
        emit(cipherStorage.decrypt(USER_MASTER_PASSWORD) == masterPassword)
    }

    fun notifyPermissionGranted() = liveData {
        emit(preferencesManager.updateRequestPassword(false))
    }

}