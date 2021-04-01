package com.nativeboys.password.manager.data.repository

import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.data.local.CategoryDao
import com.nativeboys.password.manager.data.local.FieldDao
import com.nativeboys.password.manager.data.preferences.PreferencesManager
import com.nativeboys.password.manager.other.CategoryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val fieldDao: FieldDao,
    private val preferences: PreferencesManager
) {

    suspend fun findAllCategories() = categoryDao.findAll()

    suspend fun findCategoryById(categoryId: String) = categoryDao.findById(categoryId)

    suspend fun findFieldsByCategoryId(categoryId: String) = fieldDao.findByCategoryId(categoryId)

    suspend fun updateSelectedCategoryId(id: String) = preferences.updateSelectedCategoryId(id)

    fun findAllDtoCategoriesAsFlow(): Flow<List<CategoryDto>> {
        return combine(categoryDao.findAllAsFlow(), preferences.findSelectedCategoryIdAsFlow()) { categories, categoryId ->
            categories.map {
                CategoryDto(it.id, it.name, categoryId == it.id)
            }
        }
    }

    // Sort by: 0->name, 1->date
    fun findAllCategoriesByNameAsFlow(name: String, order: Int): Flow<List<CategoryData>> {
        return if (order == 0) categoryDao.findAllByNameSortedByNameAsFlow(name)
        else categoryDao.findAllByNameSortedByNameAsFlow(name)
    }

}