package com.bangkit.skincareku.networking.response

data class Feed(
    val name: String,
    val profile: String,
    val image: String,
    val caption: String,
    val likes: Int,
    val comments: Int,
    val time: String
)
