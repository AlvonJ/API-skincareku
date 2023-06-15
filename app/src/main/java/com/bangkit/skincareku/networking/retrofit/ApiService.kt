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
        @Field("filter[]") filter: List<String>,
        @Field("method") method: String
    ): Call<GetAllProductResponse>

    @FormUrlEncoded
    @POST("products/getAllFiltered")
    fun getProductsByCategory(
        @Field("field") field: String,
        @Field("value") value: String
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

    @GET("products/getAll")
    fun getAllProducts(
    ): Call<GetAllProductResponse>

    @GET("/v2/search")
    fun getArticles(
        @Query("q") q: String,
        @Query("countries") countries: String,
        @Query("page_size") page_size: String,
        @Query("ranked_only") rankey_only: String
    ): Call<ArticleResponse>
}