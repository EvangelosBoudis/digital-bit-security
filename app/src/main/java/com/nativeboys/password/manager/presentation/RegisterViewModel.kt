package com.nativeboys.password.manager.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nativeboys.password.manager.data.MasterPasswordRequirement
import com.nativeboys.password.manager.data.UIMasterPasswordRequirement
import com.nativeboys.password.manager.data.services.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class RegisterViewModel @ViewModelInject constructor(
    private val userService: UserService
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
        emit(userService.registerUser(masterPassword))
    }

    fun isUserAlreadyRegistered() = liveData {
        emit(userService.isUserAlreadyRegistered())
    }

}