package com.example.carsdb.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM cars ORDER BY id DESC")
    fun getAll(): Flow<List<Car>>

    @Query("SELECT * FROM cars WHERE id=:id LIMIT 1")
    suspend fun getById(id: Long): Car?

    @Insert
    suspend fun insert(car: Car): Long

    @Update
    suspend fun update(car: Car)

    @Delete
    suspend fun delete(car: Car)
}
