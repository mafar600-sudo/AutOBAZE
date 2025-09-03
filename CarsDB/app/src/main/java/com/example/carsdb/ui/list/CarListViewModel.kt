package com.example.carsdb.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsdb.data.AppDatabase
import com.example.carsdb.data.Car
import com.example.carsdb.data.CarRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarListViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = CarRepository(AppDatabase.get(app).carDao())

    val cars = repo.all().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun delete(car: Car) {
        viewModelScope.launch { repo.delete(car) }
    }
}
