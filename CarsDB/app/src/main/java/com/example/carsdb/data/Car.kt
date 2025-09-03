package com.example.carsdb.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var model: String,
    var mileage: Int,
    var fuelLevel: Int
)
