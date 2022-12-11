package com.example.vic.screens.customerdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vic.domain.entities.VirtualMachineIndex
import com.example.vic.databinding.VirtualMachineRowBinding

class VirtualMachineIndexAdapter(private val clickListener: VirtualMachineIndexListener) :
    ListAdapter<VirtualMachineIndex, VirtualMachineIndexAdapter.ViewHolder>(
        VirtualMachineIndexDiffCallback()
    ) {

    class ViewHolder private constructor(private val binding: VirtualMachineRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VirtualMachineIndex, clickListener: VirtualMachineIndexListener) {
            binding.virtualMachineIndex = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = VirtualMachineRowBinding.inflate(layoutInflater, parent, false)
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

class VirtualMachineIndexDiffCallback : DiffUtil.ItemCallback<VirtualMachineIndex>() {
    override fun areItemsTheSame(
        oldItem: VirtualMachineIndex,
        newItem: VirtualMachineIndex
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: VirtualMachineIndex,
        newItem: VirtualMachineIndex
    ): Boolean {
        return oldItem == newItem
    }
}

class VirtualMachineIndexListener(val clickListener: (machineId: Long) -> Unit) {
    fun onClick(virtualMachineIndex: VirtualMachineIndex) = clickListener(virtualMachineIndex.id)
}
