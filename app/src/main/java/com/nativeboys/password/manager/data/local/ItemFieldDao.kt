package com.nativeboys.password.manager.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.nativeboys.password.manager.data.ItemFieldData

@Dao
interface ItemFieldDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(itemFields: List<ItemFieldData>)

}