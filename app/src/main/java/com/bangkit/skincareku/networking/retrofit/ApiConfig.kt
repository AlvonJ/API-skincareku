package com.bangkit.skincareku.networking.retrofit

import android.content.Context
import android.util.Log
import com.bangkit.skincareku.networking.data.DataManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private lateinit var dataManager : DataManager

        fun init(context: Context) {
            dataManager = DataManager(context)
        }


        fun getApiService(baseUrl: String): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Set the desired logging level
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor{chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("x-api-key", "dq79h_x82R2VqEy6OvbGC3OBwVSMEvTykf0aaffk_bc")
                    val request = requestBuilder.build()
                    chain.proceed(request)}
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}