package com.bangkit.skincareku.networking.database

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "cart_item")
@Parcelize
data class ItemCart(
    @ColumnInfo(name = "id")
    @PrimaryKey
    @NonNull
    var id: String,

    @ColumnInfo(name = "product_name")
    var productName: String,

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String,

    @ColumnInfo(name = "price")
    var price: String,

    @ColumnInfo(name = "isoncart")
    var isoncart: Boolean

): Parcelable