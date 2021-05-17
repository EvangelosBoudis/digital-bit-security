package com.nativeboys.password.manager.data.storage

import androidx.room.*
import com.nativeboys.password.manager.data.ContentData

@Dao
interface ContentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(contents: List<ContentData>)

    @Delete
    suspend fun delete(fields: List<ContentData>)

    @Query("DELETE FROM contents WHERE item_id = :itemId")
    suspend fun deleteAllByItemId(itemId: String)

}