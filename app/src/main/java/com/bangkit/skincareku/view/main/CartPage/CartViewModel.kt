package com.bangkit.skincareku.view.main.CartPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.skincareku.networking.database.ItemCart
import com.bangkit.skincareku.networking.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel (private val cartRepository: CartRepository) : ViewModel() {
    fun getCartItem() = cartRepository.getItemCart()

    fun addDelItem(cart: ItemCart, isoncart: Boolean) {
        viewModelScope.launch {
            if (isoncart){
                withContext(Dispatchers.IO){
                    cartRepository.deleteItem(cart,false)
                }
            } else {
                withContext(Dispatchers.IO){
                    cartRepository.addItem(cart,true)
                }
            }
        }
    }
}