package com.bangkit.skincareku.networking.retrofit

import com.bangkit.skincareku.networking.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("users/register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @PATCH("users/updateUser")
    fun updateProfile(
        @Field("email") email: String,
        @Field("gender") gender: String,
        @Field("skinProblem") skinProblem: String,
        @Field("allergy") allergy: String,
        @Field("birthDate") birthDate: String
    ): Call<UpdateUserResponse>

    @FormUrlEncoded
    @POST("products/getAllFilteredIngredients")
    fun getProducts(
        @Field("filter[]") filter: List<String>
    ): Call<GetAllProductResponse>

    @FormUrlEncoded
    @POST("users/getUserData")
    fun getUserByEmail(
        @Field("email") email: String
    ): Call<UserDataResponse>

    @Multipart
    @POST("predict")
    fun predictImage(
        @Part image: MultipartBody.Part
    ): Call<ModelResponse>

}