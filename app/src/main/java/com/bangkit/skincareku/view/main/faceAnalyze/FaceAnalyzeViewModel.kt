package com.bangkit.skincareku.view.main.faceAnalyze

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.skincareku.networking.response.GetAllProductItem
import com.bangkit.skincareku.networking.response.GetAllProductResponse
import com.bangkit.skincareku.networking.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FaceAnalyzeViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _cleanser = MutableLiveData<ArrayList<GetAllProductItem>>()
    val cleanser: LiveData<ArrayList<GetAllProductItem>> = _cleanser
    private val _moisturizer = MutableLiveData<ArrayList<GetAllProductItem>>()
    val moisturizer: LiveData<ArrayList<GetAllProductItem>> = _moisturizer
    private val _sunscreen = MutableLiveData<ArrayList<GetAllProductItem>>()
    val sunscreen: LiveData<ArrayList<GetAllProductItem>> = _sunscreen
    private val _toner = MutableLiveData<ArrayList<GetAllProductItem>>()
    val toner: LiveData<ArrayList<GetAllProductItem>> = _toner
    private val _serum = MutableLiveData<ArrayList<GetAllProductItem>>()
    val serum: LiveData<ArrayList<GetAllProductItem>> = _serum

    fun getCleanserRecommendation () {
        _isLoading.value = true

        val baseUrl = "https://services-skincareku-5ctldki4wq-et.a.run.app/"
        val client = ApiConfig.getApiService(baseUrl).getProductsByCategory("category", "Cleanser")
        client.enqueue(object : Callback<GetAllProductResponse> {
            override fun onResponse(
                call: Call<GetAllProductResponse>,
                response: Response<GetAllProductResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _cleanser.value = response.body()?.data as ArrayList<GetAllProductItem>?
                } else {
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

    fun getMoisturizerRecommendation () {
        _isLoading.value = true

        val baseUrl = "https://services-skincareku-5ctldki4wq-et.a.run.app/"
        val client = ApiConfig.getApiService(baseUrl).getProductsByCategory("category", "Moisturizer")
        client.enqueue(object : Callback<GetAllProductResponse> {
            override fun onResponse(
                call: Call<GetAllProductResponse>,
                response: Response<GetAllProductResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _moisturizer.value = response.body()?.data as ArrayList<GetAllProductItem>?
                } else {
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

    fun getSunprotectRecommendation () {
        _isLoading.value = true

        val baseUrl = "https://services-skincareku-5ctldki4wq-et.a.run.app/"
        val client = ApiConfig.getApiService(baseUrl).getProductsByCategory("category", "Sun Protect")
        client.enqueue(object : Callback<GetAllProductResponse> {
            override fun onResponse(
                call: Call<GetAllProductResponse>,
                response: Response<GetAllProductResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _sunscreen.value = response.body()?.data as ArrayList<GetAllProductItem>?
                } else {
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

    fun getTonerRecommendation () {
        _isLoading.value = true

        val baseUrl = "https://services-skincareku-5ctldki4wq-et.a.run.app/"
        val client = ApiConfig.getApiService(baseUrl).getProductsByCategory("category", "Toner")
        client.enqueue(object : Callback<GetAllProductResponse> {
            override fun onResponse(
                call: Call<GetAllProductResponse>,
                response: Response<GetAllProductResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _toner.value = response.body()?.data as ArrayList<GetAllProductItem>?
                } else {
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

    fun getSerumRecommendation () {
        _isLoading.value = true

        val baseUrl = "https://services-skincareku-5ctldki4wq-et.a.run.app/"
        val client = ApiConfig.getApiService(baseUrl).getProductsByCategory("category", "Serum")
        client.enqueue(object : Callback<GetAllProductResponse> {
            override fun onResponse(
                call: Call<GetAllProductResponse>,
                response: Response<GetAllProductResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _serum.value = response.body()?.data as ArrayList<GetAllProductItem>?
                } else {
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
}