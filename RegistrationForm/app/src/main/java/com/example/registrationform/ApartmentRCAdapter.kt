package com.example.registrationform

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationform.databinding.ApartmantitemBinding


class ApartmentRCAdapter(private var apatmansList: ArrayList<Apartmant>,): RecyclerView.Adapter<ApartmentRCAdapter.ApatmantsHolder>() {

    class ApatmantsHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ApartmantitemBinding.bind(item)
        fun bind(apatmant: Apartmant) = with(binding){
            tname.text = apatmant.name
            tadress.text=apatmant.address
            tsquare.text=apatmant.square.toString()
            tplus.text = apatmant.plus.toString()
            tminus.text = apatmant.minus.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApatmantsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.apartmantitem, parent, false)
        return ApatmantsHolder(view)
    }

    override fun onBindViewHolder(holder: ApatmantsHolder, position: Int) {
        holder.bind(apatmansList[position])
    }

    override fun getItemCount(): Int {
        return apatmansList.size
    }

//    fun addAppatmant(apatmant: Apartmant){
//        apatmansList.add(apatmant)
//        notifyDataSetChanged()
//    }
}