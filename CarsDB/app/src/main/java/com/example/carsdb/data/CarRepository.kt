package com.example.carsdb.data

class CarRepository(private val dao: CarDao) {
    fun all() = dao.getAll()
    suspend fun get(id: Long) = dao.getById(id)
    suspend fun upsert(car: Car): Long {
        return if (car.id == 0L) dao.insert(car) else {
            dao.update(car); car.id
        }
    }
    suspend fun delete(car: Car) = dao.delete(car)
}
