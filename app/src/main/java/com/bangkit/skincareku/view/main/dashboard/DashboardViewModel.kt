package com.bangkit.skincareku.view.main.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.skincareku.networking.response.*
import com.bangkit.skincareku.networking.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError
    private val _productRecommendationList = MutableLiveData<ArrayList<GetAllProductItem>>()
    val productRecommendationList: LiveData<ArrayList<GetAllProductItem>> = _productRecommendationList
    private val _articleList = MutableLiveData<ArrayList<Article>>()
    val articleList: LiveData<ArrayList<Article>> = _articleList

    private val _isConnectionError = MutableLiveData<Boolean>()
    val isConnectionError: LiveData<Boolean> = _isConnectionError
    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful
    private val _isGetProfileSuccessful = MutableLiveData<Boolean>()
    val isGetProfileSuccessful: LiveData<Boolean> = _isGetProfileSuccessful
    private val _profile = MutableLiveData<UserDataResponse>()
    val profile: LiveData<UserDataResponse> = _profile

    fun getProductRecommendation (filter: List<String>) {
        _isLoading.value = true
        println("getProductRecommendation")

        val baseUrl = "https://services-skincareku-5ctldki4wq-et.a.run.app/"
        val client = ApiConfig.getApiService(baseUrl).getProducts(filter)
        client.enqueue(object : Callback<GetAllProductResponse> {
            override fun onResponse(
                call: Call<GetAllProductResponse>,
                response: Response<GetAllProductResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    println(response.body()?.data)
                    _productRecommendationList.value = response.body()?.data as ArrayList<GetAllProductItem>?
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

    fun getArticle () {
        _isLoading.value = true
        _isError.value = false
        val article = ArrayList<Article>()
        article.add(
            Article(
                "Article 1",
                20,
            )
        )
        article.add(
            Article(
                "Article 2",
                10,
            )
        )
        article.add(
            Article(
                "Article 3",
                25,
            )
        )
        article.add(
            Article(
                "Article 4",
                20,
            )
        )
        article.add(
            Article(
                "Article 5",
                30,
            )
        )
        _articleList.value = article
        _isLoading.value = false
    }

    fun getUserByEmail(email: String) {
        _isLoading.value = true
        _isConnectionError.value = false
        _isGetProfileSuccessful.value = false

        val baseUrl = "https://services-skincareku-5ctldki4wq-et.a.run.app/"
        val client = ApiConfig.getApiService(baseUrl).getUserByEmail(email)
        client.enqueue(object : Callback<UserDataResponse> {
            override fun onResponse(
                call: Call<UserDataResponse>,
                response: Response<UserDataResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _profile.value = response.body()
                    _isGetProfileSuccessful.value = true
                } else {
                    _isGetProfileSuccessful.value = false
                }
            }

            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                _isConnectionError.value = true
                _isLoading.value = false
            }
        })
        _isLoading.value = false
    }
}