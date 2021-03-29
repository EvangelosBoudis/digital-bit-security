package com.nativeboys.password.manager.ui.categories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nativeboys.password.manager.data.CategoryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@HiltViewModel @Inject
class CategoriesViewModel @ViewModelInject constructor(
    private val categoryDao: CategoryDao
) : ViewModel() {

    val categories = categoryDao.observeAll().asLiveData()

}