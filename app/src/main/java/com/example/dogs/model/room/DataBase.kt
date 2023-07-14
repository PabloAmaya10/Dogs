package com.example.dogs.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Dog::class], version = 1)

abstract class DBRoom : RoomDatabase() {
    abstract fun dogsDao() : DogsDao

    companion object {
        @Volatile
        private var INSTANCE: DBRoom? = null
        fun getDatabase(context: Context): DBRoom {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, DBRoom::class.java, "prueba.db")
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}