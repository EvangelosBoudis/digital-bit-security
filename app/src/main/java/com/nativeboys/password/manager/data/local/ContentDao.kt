package com.nativeboys.password.manager.data.local

import androidx.room.*
import com.nativeboys.password.manager.data.ContentData

@Dao
interface ContentDao {

    @Query("SELECT * FROM contents WHERE item_id = :itemId")
    suspend fun findAllByItemId(itemId: String): List<ContentData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(contents: List<ContentData>)

    @Delete
    suspend fun delete(fields: List<ContentData>)

}