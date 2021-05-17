package com.nativeboys.password.manager.presentation

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nativeboys.password.manager.data.services.UserService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val userService: UserService
): ViewModel() {

    val darkThemeMode = userService.observeDarkThemeMode().asLiveData()

    fun initIfRequired() = viewModelScope.launch(context = Dispatchers.IO) {
        userService.initDatabase(context)
    }

}