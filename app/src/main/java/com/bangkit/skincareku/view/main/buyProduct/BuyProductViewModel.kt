package com.bangkit.skincareku.view.main.buyProduct

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.skincareku.networking.response.GetAllProductItem
import com.bangkit.skincareku.networking.response.GetAllProductResponse
import com.bangkit.skincareku.networking.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuyProductViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError
    private val _productList = MutableLiveData<ArrayList<GetAllProductItem>>()
    val productList: LiveData<ArrayList<GetAllProductItem>> = _productList
    private val _filteredProductList = MutableLiveData<ArrayList<GetAllProductItem>>()
    val filteredProductList: LiveData<ArrayList<GetAllProductItem>> = _filteredProductList

    fun getProductAll () {
        _isLoading.value = true
        println("getProductAll")

        val baseUrl = "https://services-skincareku-5ctldki4wq-et.a.run.app/"
        val client = ApiConfig.getApiService(baseUrl).getAllProducts()
        client.enqueue(object : Callback<GetAllProductResponse> {
            override fun onResponse(
                call: Call<GetAllProductResponse>,
                response: Response<GetAllProductResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    println(response.body()?.data)
                    _productList.value = response.body()?.data as ArrayList<GetAllProductItem>?
                } else {
                    println(response.errorBody().toString())
                    println(response.code())
                    println(response.raw())
                    Log.d("Error", response.message())
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<GetAllProductResponse>, t: Throwable) {
                println("onFailure")
                _isError.value = true
                _isLoading.value = false
            }
        })
    }

    fun searchProduct(query: String) {
        val productList = _productList.value
        if (productList != null) {
            val filteredList = if (query.isNotEmpty()) {
                productList.filter { item ->
                    item.data.productName.contains(query, ignoreCase = true)
                } as ArrayList<GetAllProductItem>
            } else {
                productList
            }
            _filteredProductList.value = filteredList
        }
    }
}