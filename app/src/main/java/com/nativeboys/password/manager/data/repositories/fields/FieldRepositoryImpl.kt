package com.nativeboys.password.manager.data.repositories.fields

import com.nativeboys.password.manager.data.FieldData
import com.nativeboys.password.manager.data.storage.FieldDao
import javax.inject.Inject

class FieldRepositoryImpl @Inject constructor(
    private val fieldDao: FieldDao
): FieldRepository {

    override suspend fun findAllFieldsByCategoryId(categoryId: String): List<FieldData> {
        return fieldDao.findAllByCategoryId(categoryId)
    }

}