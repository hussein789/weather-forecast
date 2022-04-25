package com.hussien.weatherforecast.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hussien.weatherforecast.data.model.Location
import com.hussien.weatherforecast.databinding.CityItemLayoutBinding

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>()  {

    private var locations = mutableListOf<Location>()
    lateinit var callback: LocationCallback

    fun setData(list:List<Location>){
        locations.clear()
        locations.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CityItemLayoutBinding.inflate(inflater,parent,false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    inner class LocationViewHolder(private val binding:CityItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.itemContainer.setOnClickListener {
                callback.onLocationClicked(locations[adapterPosition])
            }
        }
        fun bind(location:Location){
            binding.cityNameTv.text = location.name
            binding.regionNameTv.text = location.region
            binding.dashTv.isVisible = location.region?.isNotEmpty() == true

        }
    }
}

interface LocationCallback{
    fun onLocationClicked(location: Location)
}