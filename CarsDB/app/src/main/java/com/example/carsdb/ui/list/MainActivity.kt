package com.example.carsdb.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carsdb.databinding.ActivityMainBinding
import com.example.carsdb.ui.edit.CarEditActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CarListViewModel by viewModels()
    private val adapter = CarAdapter(
        onClick = { car ->
            val i = Intent(this, CarEditActivity::class.java)
            i.putExtra(CarEditActivity.EXTRA_ID, car.id)
            startActivity(i)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, CarEditActivity::class.java))
        }

        lifecycleScope.launch {
            viewModel.cars.collectLatest { list ->
                adapter.submitList(list)
            }
        }
    }
}
