package com.nativeboys.password.manager.data.repositories.fields

import com.nativeboys.password.manager.data.FieldData

interface FieldRepository {

    suspend fun findAllFieldsByCategoryId(categoryId: String): List<FieldData>

}