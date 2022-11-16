package com.example.vic.screens.customerlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vic.database.entities.CustomerIndex
import com.example.vic.databinding.CustomerRowBinding

class CustomerIndexAdapter(val clickListener: CustomerIndexListener) :
    ListAdapter<CustomerIndex, CustomerIndexAdapter.ViewHolder>(CustomerIndexDiffCallback()) {

    class ViewHolder private constructor(private val binding: CustomerRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CustomerIndex, clickListener: CustomerIndexListener) {
            binding.customerIndex = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomerRowBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}


class CustomerIndexDiffCallback : DiffUtil.ItemCallback<CustomerIndex>() {
    override fun areItemsTheSame(oldItem: CustomerIndex, newItem: CustomerIndex): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CustomerIndex, newItem: CustomerIndex): Boolean {
        return oldItem == newItem
    }
}

class CustomerIndexListener(val clickListener: (customerId: Long) -> Unit) {
    fun onClick(customerIndex: CustomerIndex) = clickListener(customerIndex.id)
}