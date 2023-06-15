package com.bangkit.skincareku.view.main.CartPage

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skincareku.networking.repository.CartRepository

class ViewModelFactory private constructor(private val cartRepository: CartRepository):
ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)){
            return CartViewModel(cartRepository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class: ${modelClass.name}")
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }

}