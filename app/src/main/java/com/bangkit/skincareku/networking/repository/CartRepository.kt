package com.bangkit.skincareku.networking.repository

import androidx.lifecycle.LiveData
import com.bangkit.skincareku.networking.database.ItemCart
import com.bangkit.skincareku.networking.database.ItemCartDao
import com.bangkit.skincareku.networking.retrofit.ApiService

class CartRepository(
    private val apiService: ApiService,
    private val itemCartDao: ItemCartDao) {

    fun getItemCart(): LiveData<List<ItemCart>>{
        return itemCartDao.getItemCart()
    }

    fun addItem(cart: ItemCart, conditionCart: Boolean){
        cart.isoncart = conditionCart
        itemCartDao.insert(cart)
    }

    fun deleteItem(cart: ItemCart, conditionCart: Boolean){
        cart.isoncart = conditionCart
        itemCartDao.delete(cart)
    }

    companion object{
        @Volatile
        private var instance: CartRepository? = null
        fun getInstance(
            apiService: ApiService,
            itemCartDao: ItemCartDao
        ): CartRepository = instance?: synchronized(this){
            instance?: CartRepository(apiService, itemCartDao)
        }.also { instance = it }
    }
}