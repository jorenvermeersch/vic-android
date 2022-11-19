package com.example.vic.screens.virtualmachinedetails


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vic.database.entities.Credentials
import com.example.vic.databinding.CredentialsRowBinding

class CredentialsAdapter :
    ListAdapter<Credentials, CredentialsAdapter.ViewHolder>(
        CredentialsDiffCallback()
    ) {

    class ViewHolder private constructor(private val binding: CredentialsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Credentials) {
            binding.credentials = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CredentialsRowBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class CredentialsDiffCallback : DiffUtil.ItemCallback<Credentials>() {
    override fun areItemsTheSame(oldItem: Credentials, newItem: Credentials): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Credentials, newItem: Credentials): Boolean {
        return oldItem == newItem
    }
}
