package com.infinity.mental.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.infinity.mental.data.model.result.Gejala_Nilai
import com.infinity.mental.data.model.result.Pakar
import com.infinity.mental.databinding.ItemUserValueBinding
import javax.inject.Inject
import javax.inject.Singleton

class AdapterUserValue (private val gejalaList: List<Any>) : RecyclerView.Adapter<AdapterUserValue.ViewHolder>() {
    inner class ViewHolder(val binding: ItemUserValueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Gejala_Nilai) {
            binding.tvSymptom.text = data.kode
            binding.tvValue.text = data.skor.toString()
            binding.tvQuestion.text = data.question
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemUserValueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = gejalaList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = gejalaList[position] as Gejala_Nilai
        holder.bind(data)
    }
}