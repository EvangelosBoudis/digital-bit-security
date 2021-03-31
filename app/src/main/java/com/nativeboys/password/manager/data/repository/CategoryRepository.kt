package com.nativeboys.password.manager.data.repository

import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.data.local.CategoryDao
import com.nativeboys.password.manager.data.local.FieldDao
import com.nativeboys.password.manager.other.CategoryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val fieldDao: FieldDao
) {

    fun findAllAsFlow() = categoryDao.findAllAsFlow()

/*    fun findAllWithFavoritesAsFlow(id: Flow<String>) {

        combine(id, find)

        findAllAsFlow().map {
            it.map {
                CategoryDto(it.)
            }

            val categories = it.toMutableList()

            categories.add(CategoryDto("FAVORITES", "Favorites", true))
        }
    }*/

    suspend fun findAll() = categoryDao.findAll()

    suspend fun findCategoryById(categoryId: String) = categoryDao.findById(categoryId)

    suspend fun findCategoryFields(categoryId: String) = fieldDao.findByCategoryId(categoryId)

    // Sort by: 0->name, 1->date
    fun findAllCategoriesByName(name: String, order: Int): Flow<List<CategoryData>> {
        return if (order == 0) categoryDao.findAllByNameSortedByName(name)
        else categoryDao.findAllByNameSortedByName(name)
    }



}