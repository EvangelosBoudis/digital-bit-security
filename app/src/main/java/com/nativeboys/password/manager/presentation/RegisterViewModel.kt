package com.nativeboys.password.manager.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.github.leonardoxh.keystore.CipherStorage
import com.nativeboys.password.manager.BuildConfig.USER_MASTER_PASSWORD
import com.nativeboys.password.manager.data.MasterPasswordRequirement
import com.nativeboys.password.manager.data.UIMasterPasswordRequirement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class RegisterViewModel @ViewModelInject constructor(
    private val cipherStorage: CipherStorage
): ViewModel() {

    val password = MutableStateFlow("")

    val masterPasswordRequirements = password.map { password ->
        MasterPasswordRequirement.available.map { requirement ->
            UIMasterPasswordRequirement(
                requirement.descriptionResId,
                Regex(requirement.regularExpression).matches(password)
            )
        }
    }.asLiveData()

    val registerBtnEnabled = masterPasswordRequirements.map {
        it.firstOrNull { requirement ->
            !requirement.accepted
        } == null
    }

    fun register(masterPassword: String) = liveData(Dispatchers.IO) {
        emit(cipherStorage.encrypt(USER_MASTER_PASSWORD, masterPassword))
    }

    fun isUserAlreadyRegistered() = liveData {
        emit(cipherStorage.containsAlias(USER_MASTER_PASSWORD))
    }

}