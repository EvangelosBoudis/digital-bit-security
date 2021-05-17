package com.nativeboys.password.manager.data.repositories.category

import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.data.CategoryDto
import com.nativeboys.password.manager.data.CategoryWithFields
import com.nativeboys.password.manager.data.FieldData
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun countAllCategories(): Int

    suspend fun findCategoryById(categoryId: String): CategoryData

    suspend fun findCategoryWithFieldsById(categoryId: String): CategoryWithFields?

    suspend fun findAllCategories(): List<CategoryData>

    fun observeAllCategories(): Flow<List<CategoryData>>

    fun observeAllCategoriesDto(addDefault: Boolean = true): Flow<List<CategoryDto>>

    fun observeAllCategoriesByName(name: String, order: Int): Flow<List<CategoryData>>

    suspend fun updateSelectedCategoryId(id: String)

    suspend fun deleteCategoryById(id: String)

    suspend fun editCategory(category: CategoryData, fields: List<FieldData>, save: Boolean)

}