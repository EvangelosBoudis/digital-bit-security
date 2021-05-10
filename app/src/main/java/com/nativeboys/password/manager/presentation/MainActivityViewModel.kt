package com.nativeboys.password.manager.presentation

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativeboys.password.manager.BuildConfig.DATABASE_BACKUP
import com.nativeboys.password.manager.data.local.AppDatabase
import com.nativeboys.password.manager.data.repository.CategoryRepository
import com.nativeboys.password.manager.util.storage.FileUtils
import ir.androidexception.roomdatabasebackupandrestore.Restore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class MainActivityViewModel @ViewModelInject constructor(
    private val database: AppDatabase,
    private val categoryRepository: CategoryRepository
): ViewModel() {

    fun initIfRequired(context: Context) {
        viewModelScope.launch(context = Dispatchers.IO) {
            if (categoryRepository.countAllCategories() != 0) return@launch
            FileUtils.copyFileFromAssetsToMainDirectory(
                context,
                "database/database_init.json",
                DATABASE_BACKUP
            )
            Restore.Init()
                .database(database)
                .backupFilePath(context.externalCacheDir?.path + File.separator + DATABASE_BACKUP)
                .execute()
        }
    }

}