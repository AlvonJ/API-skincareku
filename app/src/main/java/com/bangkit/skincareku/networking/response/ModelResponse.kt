package com.bangkit.skincareku.networking.response

import com.google.gson.annotations.SerializedName

data class ModelResponse(

	@field:SerializedName("prediction")
	val prediction: Map<String, Double>
)

data class Prediction(

	@field:SerializedName("comedo")
	val comedo: Double,

	@field:SerializedName("acne")
	val acne: Double,

	@field:SerializedName("clean")
	val clean: Double
)
