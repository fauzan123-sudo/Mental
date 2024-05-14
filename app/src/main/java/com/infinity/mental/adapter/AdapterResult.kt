package com.infinity.mental.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.infinity.mental.databinding.ItemValueBinding

class AdapterResult(private val arrayDouble: List<Double>) :
    RecyclerView.Adapter<AdapterResult.ViewHolder>() {
    inner class ViewHolder(val binding: ItemValueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Double) {
            val formattedCf = String.format("%.2f", data)
            binding.tvSymptom.text = formattedCf
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemValueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = arrayDouble.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data = arrayDouble[position]
        holder.bind(data)
    }
}