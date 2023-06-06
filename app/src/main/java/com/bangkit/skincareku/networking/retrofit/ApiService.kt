package com.bangkit.skincareku.networking.retrofit

import com.bangkit.skincareku.networking.response.Product
import com.bangkit.skincareku.networking.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("users/register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @GET("products")
    fun getProducts(): Call<Product>

}