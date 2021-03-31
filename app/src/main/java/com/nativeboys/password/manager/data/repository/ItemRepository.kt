package com.nativeboys.password.manager.data.repository

import com.nativeboys.password.manager.data.local.ItemDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val itemDao: ItemDao
) {

    

}