package com.nativeboys.password.manager.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nativeboys.password.manager.data.services.UserService

class LoginViewModel @ViewModelInject constructor(
    private val userService: UserService,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    val destination: Int? = state.get<Int>("DESTINATION")
    val data: String? = state.get<String>("DATA")

    fun requestPermission(masterPassword: String) = liveData {
        emit(userService.requestPermission(masterPassword))
    }

}