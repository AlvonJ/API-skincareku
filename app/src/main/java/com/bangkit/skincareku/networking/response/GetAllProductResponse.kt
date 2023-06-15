package com.bangkit.skincareku.networking.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetAllProductResponse(

	@field:SerializedName("data")
	val data: List<GetAllProductItem>,

	@field:SerializedName("status")
	val status: String
)
@Parcelize
data class GetAllProductItem(

	@field:SerializedName("data")
	val data: GetAllProductData,

	@field:SerializedName("id")
	val id: String
): Parcelable

@Parcelize
data class GetAllProductData(

	@field:SerializedName("product_url")
	val productUrl: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("Rating")
	val rating: Int,

	@field:SerializedName("ingredients")
	val ingredients: List<String>,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("bad_reviews")
	val badReviews: Int,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("product_name")
	val productName: String,

	@field:SerializedName("brand")
	val brand: String,

	@field:SerializedName("good_reviews")
	val goodReviews: Int
) : Parcelable
