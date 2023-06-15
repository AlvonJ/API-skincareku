package com.bangkit.skincareku.networking.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ItemCart::class], version = 1)
abstract class CartRoomDatabase : RoomDatabase() {
    abstract fun itemCartDao() : ItemCartDao

    companion object{
        @Volatile
        private var INSTANCE : CartRoomDatabase? = null
        fun getInstance (context: Context) : CartRoomDatabase = INSTANCE?: synchronized(this){
            INSTANCE?: Room.databaseBuilder(
                context.applicationContext,
                CartRoomDatabase::class.java, "cart_database"
            ).build()
        }
    }
}