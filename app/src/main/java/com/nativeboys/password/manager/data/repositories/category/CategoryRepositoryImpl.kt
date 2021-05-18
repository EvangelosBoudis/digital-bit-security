package com.nativeboys.password.manager.data.repositories.category

import androidx.room.withTransaction
import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.data.CategoryDto
import com.nativeboys.password.manager.data.CategoryWithFields
import com.nativeboys.password.manager.data.FieldData
import com.nativeboys.password.manager.data.storage.AppDatabase
import com.nativeboys.password.manager.data.storage.CategoryDao
import com.nativeboys.password.manager.data.storage.FieldDao
import com.nativeboys.password.manager.data.preferences.PreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.*
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val categoryDao: CategoryDao,
    private val fieldDao: FieldDao,
    private val preferences: PreferencesManager
): CategoryRepository {

    override suspend fun countAllCategories(): Int {
        return categoryDao.countAll()
    }

    override suspend fun findCategoryById(categoryId: String): CategoryData {
        return categoryDao.findById(categoryId)
    }

    override suspend fun findCategoryWithFieldsById(categoryId: String): CategoryWithFields? {
        return categoryDao.findCategoryWithFieldsById(categoryId)
    }

    override suspend fun findAllCategories(): List<CategoryData> {
        return categoryDao.findAll()
    }

    override fun observeAllCategories(): Flow<List<CategoryData>> {
        return categoryDao.observeAll()
    }

    override fun observeAllCategoriesDto(addDefault: Boolean): Flow<List<CategoryDto>> {
        return combine(categoryDao.observeAll(), preferences.observeSelectedCategoryId()) { categories, categoryId ->
            val mutable = categories.map {
                CategoryDto(it.id, it.name, categoryId == it.id)
            }.toMutableList()
            if (addDefault) mutable.add(0, CategoryDto("", "All", categoryId == "")) // default (All Categories)
            return@combine mutable
        }
    }

    /** Sort by: 0-> name, 1-> date */
    override fun observeAllCategoriesByName(name: String, order: Int): Flow<List<CategoryData>> {
        return if (order == 0) categoryDao.observeAllByNameSortedByName(name)
        else categoryDao.observeAllByNameSortedByName(name)
    }

    override suspend fun updateSelectedCategoryId(id: String) {
        return preferences.updateSelectedCategoryId(id)
    }

    override suspend fun deleteCategoryById(id: String) {
        return categoryDao.deleteById(id)
    }

    override suspend fun editCategory(
        category: CategoryData,
        fields: List<FieldData>,
        save: Boolean
    ) {
        val fieldIds = fieldDao.findAllIdsByCategoryId(category.id)
        val saveFields = fields.filter { !fieldIds.contains(it.id) }
        val updateFields = fields.filter { fieldIds.contains(it.id) }

        db.withTransaction {
            if (save) categoryDao.save(category) else categoryDao.update(category)
            fieldDao.deleteAllExcept(category.id, fields.map { it.id })
            fieldDao.save(saveFields)
            fieldDao.update(updateFields)
        }
    }

}