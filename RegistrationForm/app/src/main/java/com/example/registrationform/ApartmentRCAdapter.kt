package com.example.registrationform

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationform.databinding.ApartmantitemBinding


class ApartmentRCAdapter: RecyclerView.Adapter<ApartmentRCAdapter.PlantHolder>() {
    val plantList = ArrayList<Apartmant>()
    class PlantHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ApartmantitemBinding.bind(item)
        fun bind(apatmant: Apartmant) = with(binding){
            tname.text = apatmant.name
            tadress.text=apatmant.address
            tsquare.text=apatmant.square.toString()
            tplus.text = apatmant.plus.toString()
            tminus.text = apatmant.minus.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.apartmantitem, parent, false)
        return PlantHolder(view)
    }

    override fun onBindViewHolder(holder: PlantHolder, position: Int) {
        holder.bind(plantList[position])
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    fun addAppatmant(apatmant: Apartmant){
        plantList.add(apatmant)
        notifyDataSetChanged()
    }
}