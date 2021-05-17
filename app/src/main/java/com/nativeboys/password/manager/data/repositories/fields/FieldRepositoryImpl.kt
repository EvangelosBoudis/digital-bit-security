package com.nativeboys.password.manager.data.repositories.fields

import com.nativeboys.password.manager.data.FieldData
import com.nativeboys.password.manager.data.storage.FieldDao

class FieldRepositoryImpl constructor(
    private val fieldDao: FieldDao
): FieldRepository {

    override suspend fun findAllFieldsByCategoryId(categoryId: String): List<FieldData> {
        return fieldDao.findAllByCategoryId(categoryId)
    }

}