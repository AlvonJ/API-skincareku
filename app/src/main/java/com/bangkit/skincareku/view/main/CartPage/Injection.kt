package com.bangkit.skincareku.view.main.CartPage

import android.content.Context
import com.bangkit.skincareku.networking.database.CartRoomDatabase
import com.bangkit.skincareku.networking.repository.CartRepository
import com.bangkit.skincareku.networking.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): CartRepository{
        val apiService = ApiConfig.getApiService(baseUrl = "https://services-skincareku-5ctldki4wq-et.a.run.app/")
        val database = CartRoomDatabase.getInstance(context)
        val dao = database.itemCartDao()
        return CartRepository.getInstance(apiService,dao)
    }
}