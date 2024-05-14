package com.infinity.mental.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.infinity.mental.R
import com.infinity.mental.data.model.quisioner.KondisiUser
import com.infinity.mental.databinding.ItemAssessmentBinding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterAssessment @Inject constructor() :
    RecyclerView.Adapter<AdapterAssessment.ViewHolder>() {

    private var selected = -1
    var listener: ItemListener? = null

    interface ItemListener {
        fun itemSelected(data: KondisiUser)
    }

    inner class ViewHolder(val binding: ItemAssessmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userCondition: KondisiUser) {
            binding.tvAssessment.text = userCondition.kondisi
            binding.root.setOnClickListener {
                listener?.itemSelected(userCondition)
                setSingleSelection(adapterPosition)
            }
            if (selected == adapterPosition) {
                binding.imgAssessment.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    ), PorterDuff.Mode.SRC_IN
                )
                binding.tvAssessment.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )

                binding.rbAssessment.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    ), PorterDuff.Mode.SRC_IN
                )

                binding.mcvAssessment.strokeWidth =
                    binding.root.context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._3sdp)

                binding.rbAssessment.setImageResource(R.drawable.ic_radio_active)

                binding.rowAssessment.setBackgroundResource(R.color.primary)
            } else {
                binding.imgAssessment.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.secondary
                    ), PorterDuff.Mode.SRC_IN
                )

                binding.rbAssessment.setColorFilter(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.secondary
                    ), PorterDuff.Mode.SRC_IN
                )

                binding.tvAssessment.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.secondary
                    )
                )
                binding.mcvAssessment.strokeWidth = 0
                binding.rbAssessment.setImageResource(R.drawable.ic_radio_unactive)
                binding.rowAssessment.setBackgroundResource(R.color.white)
            }

        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<KondisiUser>() {
        override fun areItemsTheSame(oldItem: KondisiUser, newItem: KondisiUser): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: KondisiUser, newItem: KondisiUser): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemAssessmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userCondition = differ.currentList[position]
        holder.bind(userCondition)
//        holder.binding.tvAssessment.text = userCondition.kondisi
//        if (selected == position) {
//            holder.binding.rowAssessment.setBackgroundResource(R.color.orange)
//        } else {
//            holder.binding.rowAssessment.setBackgroundResource(R.color.white)
//        }
    }

    private fun setSingleSelection(position: Int) {
        if (position == RecyclerView.NO_POSITION) return
        notifyItemChanged(selected)
        selected = position
        notifyItemChanged(selected)
    }

    fun setSelected(position: Int) {
        val previousSelected = selected
        selected = position
        notifyItemChanged(previousSelected)
        notifyItemChanged(selected)
    }


}