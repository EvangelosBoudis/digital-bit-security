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

    suspend fun saveOrUpdateCategory(
        id: String?,
        name: String,
        thumbnailCode: String,
        fields: List<FieldData>
    ) {

        val userId = UUID.randomUUID().toString() // TODO: Change
        val categoryId = id ?: UUID.randomUUID().toString()

        val category = CategoryData(categoryId, name, thumbnailCode, userId)

        val fieldIds = fieldDao.findAllIdsByCategoryId(categoryId)

        val saveFields = fields.filter {
            !fieldIds.contains(it.id)
        }

        val updateFields = fields.filter {
            fieldIds.contains(it.id)
        }

        database.withTransaction {
            if (id == null) categoryDao.save(category) else categoryDao.update(category)
            fieldDao.deleteAllExcept(categoryId, fields.map { it.id })
            fieldDao.save(saveFields)
            fieldDao.update(updateFields)
        }

    }

}