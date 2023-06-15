package com.bangkit.skincareku.networking.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemCartDao {
    @Query("SELECT * FROM cart_item WHERE isoncart = 1")
    fun getItemCart() : LiveData<List<ItemCart>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cart: ItemCart)

    @Delete
    fun delete(cart: ItemCart)
}