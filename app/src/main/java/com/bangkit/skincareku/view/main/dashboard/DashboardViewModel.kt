package com.bangkit.skincareku.view.main.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.skincareku.networking.response.Product

class DashboardViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError
    private val _productRecommendationList = MutableLiveData<ArrayList<Product>>()
    val productRecommendationList: LiveData<ArrayList<Product>> = _productRecommendationList

    fun getProductRecommendation () {
        _isLoading.value = true
        _isError.value = false
        val productRecommendation = ArrayList<Product>()
        productRecommendation.add(
            Product(
                "Product 1",
                "Description 1",
                10
            )
        )
        productRecommendation.add(
            Product(
                "Product 2",
                "Description 2",
                20
            )
        )
        productRecommendation.add(
            Product(
                "Product 3",
                "Description 3",
                30
            )
        )
        productRecommendation.add(
            Product(
                "Product 4",
                "Description 4",
                40
            )
        )
        productRecommendation.add(
            Product(
                "Product 5",
                "Description 5",
                50
            )
        )
        _productRecommendationList.value = productRecommendation
        _isLoading.value = false
    }
}