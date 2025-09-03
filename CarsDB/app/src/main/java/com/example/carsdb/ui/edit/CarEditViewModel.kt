package com.example.carsdb.ui.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.carsdb.data.AppDatabase
import com.example.carsdb.data.Car
import com.example.carsdb.data.CarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CarEditViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = CarRepository(AppDatabase.get(app).carDao())

    suspend fun load(id: Long): Car? = withContext(Dispatchers.IO) {
        repo.get(id)
    }

    fun save(model: String, mileage: Int, fuel: Int, id: Long?, onSaved: (Long) -> Unit) {
        viewModelScope.launch {
            val car = Car(id ?: 0L, model.trim(), mileage, fuel)
            val newId = repo.upsert(car)
            onSaved(newId)
        }
    }

    fun delete(car: Car, onDone: () -> Unit) {
        viewModelScope.launch {
            repo.delete(car)
            onDone()
        }
    }
}
