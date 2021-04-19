package com.nativeboys.password.manager.data.repository

import com.nativeboys.password.manager.data.local.FieldDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FieldRepository @Inject constructor(
    private val fieldDao: FieldDao
) {

    suspend fun findAllFieldsByCategoryId(categoryId: String) = fieldDao.findAllByCategoryId(categoryId)

}