package com.example.carsdb.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.carsdb.R
import com.example.carsdb.data.Car

class CarAdapter(private val onClick: (Car) -> Unit) :
    ListAdapter<Car, CarAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Car>() {
            override fun areItemsTheSame(oldItem: Car, newItem: Car) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Car, newItem: Car) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return VH(view, onClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    class VH(itemView: View, private val onClick: (Car) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val tvModel: TextView = itemView.findViewById(R.id.tvModel)
        private val tvMileage: TextView = itemView.findViewById(R.id.tvMileage)
        private val tvFuel: TextView = itemView.findViewById(R.id.tvFuel)
        private var current: Car? = null

        init {
            itemView.setOnClickListener { current?.let(onClick) }
        }

        fun bind(car: Car) {
            current = car
            tvModel.text = car.model
            tvMileage.text = "Пробег: ${car.mileage} км"
            tvFuel.text = "Топливо: ${car.fuelLevel}%"
        }
    }
}
