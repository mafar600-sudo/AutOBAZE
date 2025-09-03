package com.example.carsdb.ui.edit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.carsdb.data.Car
import com.example.carsdb.databinding.ActivityEditCarBinding
import kotlinx.coroutines.launch

class CarEditActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "id"
    }

    private lateinit var binding: ActivityEditCarBinding
    private val viewModel: CarEditViewModel by viewModels()

    private var loadedCar: Car? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getLongExtra(EXTRA_ID, 0L).takeIf { it != 0L }

        if (id != null) {
            lifecycleScope.launch {
                loadedCar = viewModel.load(id)
                loadedCar?.let { car ->
                    binding.etModel.setText(car.model)
                    binding.etMileage.setText(car.mileage.toString())
                    binding.etFuel.setText(car.fuelLevel.toString())
                    binding.btnDelete.visibility = View.VISIBLE
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val model = binding.etModel.text?.toString().orEmpty()
            val mileage = binding.etMileage.text?.toString()?.toIntOrNull() ?: 0
            val fuel = binding.etFuel.text?.toString()?.toIntOrNull() ?: 0

            if (model.isBlank()) {
                Toast.makeText(this, "Введите модель", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (fuel !in 0..100) {
                Toast.makeText(this, "Топливо должно быть 0..100%", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.save(model, mileage, fuel, id) { newId ->
                Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.btnDelete.setOnClickListener {
            loadedCar?.let { car ->
                lifecycleScope.launch {
                    viewModel.delete(car) {
                        Toast.makeText(this@CarEditActivity, "Удалено", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }
}
