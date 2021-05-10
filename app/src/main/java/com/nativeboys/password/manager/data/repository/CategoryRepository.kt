package com.nativeboys.password.manager.data.repository

import androidx.room.withTransaction
import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.data.CategoryDto
import com.nativeboys.password.manager.data.FieldData
import com.nativeboys.password.manager.data.local.AppDatabase
import com.nativeboys.password.manager.data.local.CategoryDao
import com.nativeboys.password.manager.data.local.FieldDao
import com.nativeboys.password.manager.data.preferences.PreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val database: AppDatabase,
    private val categoryDao: CategoryDao,
    private val fieldDao: FieldDao,
    private val preferences: PreferencesManager
) {

    suspend fun countAllCategories() = categoryDao.countAll()

    fun observeAllCategories() = categoryDao.observeAll()

    suspend fun findAllCategories() = categoryDao.findAll()

    suspend fun findCategoryById(categoryId: String) = categoryDao.findById(categoryId)

    suspend fun findFieldsByCategoryId(categoryId: String) = fieldDao.findAllByCategoryId(categoryId)

    suspend fun findCategoryWithFieldsById(categoryId: String) = categoryDao.findCategoryWithFieldsById(categoryId)

    suspend fun updateSelectedCategoryId(id: String) = preferences.updateSelectedCategoryId(id)

    fun observeAllCategoriesDto(addDefault: Boolean = true): Flow<List<CategoryDto>> {
        return combine(categoryDao.observeAll(), preferences.observeSelectedCategoryId()) { categories, categoryId ->
            val mutable = categories.map {
                CategoryDto(it.id, it.name, categoryId == it.id)
            }.toMutableList()
            if (addDefault) mutable.add(0, CategoryDto("", "All", categoryId == "")) // default (All Categories)
            return@combine mutable
        }
    }

    // Sort by: 0->name, 1->date
    fun observeAllCategoriesByName(name: String, order: Int): Flow<List<CategoryData>> {
        return if (order == 0) categoryDao.observeAllByNameSortedByName(name)
        else categoryDao.observeAllByNameSortedByName(name)
    }

    suspend fun deleteCategoryById(id: String) = categoryDao.deleteById(id)

    suspend fun editCategory(
        category: CategoryData,
        fields: List<FieldData>,
        save: Boolean
    ) {

        val fieldIds = fieldDao.findAllIdsByCategoryId(category.id)
        val saveFields = fields.filter { !fieldIds.contains(it.id) }
        val updateFields = fields.filter { fieldIds.contains(it.id) }

        database.withTransaction {
            if (save) categoryDao.save(category) else categoryDao.update(category)
            fieldDao.deleteAllExcept(category.id, fields.map { it.id })
            fieldDao.save(saveFields)
            fieldDao.update(updateFields)
        }

    }

}