package com.infinity.mental.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.infinity.mental.data.model.result.Pakar
import com.infinity.mental.databinding.ItemExpertBinding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterExpert @Inject constructor() : RecyclerView.Adapter<AdapterExpert.ViewHolder>() {
    inner class ViewHolder(val binding: ItemExpertBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(expert: Pakar?, position: Int) {
            val number = position + 1
            binding.tvNo.text = number.toString()
            binding.tvSymptom.text = expert?.kode_gejala + "|" + expert?.kode_depresi
            val totalSymptom = expert?.mb?.minus(expert.md) ?: 0.0
            val formattedCf = String.format("%.2f", totalSymptom)
            binding.tvValue.text = formattedCf
            binding.tvGejala.text = expert?.gejala
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemExpertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    private val differCallback = object : DiffUtil.ItemCallback<Pakar>() {
        override fun areItemsTheSame(oldItem: Pakar, newItem: Pakar): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Pakar,
            newItem: Pakar
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expert = differ.currentList[position]
        holder.bind(expert, position)
    }
}