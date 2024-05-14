package com.infinity.mental.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.infinity.mental.data.model.blog.Data
import com.infinity.mental.databinding.ItemHomeBinding
import com.infinity.mental.utils.Constants.BASE_IMAGE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterBlog @Inject constructor() : RecyclerView.Adapter<AdapterBlog.ViewHolder>() {

    var listener: ItemListener? = null

    interface ItemListener {
        fun clicked(data: Data)
    }

    inner class ViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(blog: Data?) {
            binding.apply {
                Glide.with(itemView)
                    .load(BASE_IMAGE + blog?.url_gambar)
//                    .error(R.drawable.ic_no_image)
//                    .placeholder(R.drawable.progress_animation)
                    .into(binding.ivContent)

                binding.root.setOnClickListener {
                    if (blog != null) {
                        listener?.clicked(blog)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    private val differCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Data,
            newItem: Data
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog = differ.currentList[position]
        holder.bind(blog)
    }
}