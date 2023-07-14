package com.example.dogs.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DogsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dog: Dog)

    @Query("SELECT * FROM dogs")
    fun getAll(): Flow<List<Dog>>
}