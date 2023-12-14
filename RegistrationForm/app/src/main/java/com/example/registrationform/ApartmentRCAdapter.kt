package com.example.registrationform

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registrationform.databinding.ApartmantitemBinding


class ApartmentRCAdapter(): RecyclerView.Adapter<ApartmentRCAdapter.ApatmantsHolder>() {
    var apatmans= mutableListOf<Apartmant>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onItemClick: ((Apartmant) -> Unit)? = null

    inner class ApatmantsHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ApartmantitemBinding.bind(item)
        fun bind(apatmant: Apartmant) = with(binding){
            tname.text = apatmant.name
            tadress.text=apatmant.address
            tsquare.text=apatmant.square.toString()
            tplus.text = apatmant.plus.toString()
            tminus.text = apatmant.minus.toString()
        }
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(apatmans[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApatmantsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.apartmantitem, parent, false)
        return ApatmantsHolder(view)
    }

    override fun onBindViewHolder(holder: ApatmantsHolder, position: Int) {
        holder.bind(apatmans[position])
    }

    override fun getItemCount(): Int {
        return apatmans.size
    }

//    fun addAppatmant(apatmant: Apartmant){
//        apatmansList.add(apatmant)
//        notifyDataSetChanged()
//    }
}