package com.example.biopin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.biopin.database.models.Logs
import com.example.biopin.database.models.User
import com.example.biopin.databinding.LogsItemBinding
import com.google.android.gms.maps.GoogleMap

class LogsAdapter(
    private val onDeleteClicked: (Logs) -> Unit,
    private val onUpdateClicked: (Logs) -> Unit
): ListAdapter<Logs, LogsAdapter.LogsViewHolder>(DiffCallback) {

    class LogsViewHolder(
        binding: LogsItemBinding
    ): RecyclerView.ViewHolder(binding.root){
        val logs = binding.logsView
        val coordinates = binding.coordinatesView
        val remarks = binding.remarksView
        val updateButton = binding.updateButton
        val deleteButton = binding.deleteButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        return LogsViewHolder(
            LogsItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        val item = getItem(position)
        holder.logs.text = item.User
        holder.coordinates.text = item.Coordinates
        holder.remarks.text = item.Remarks

        holder.updateButton.setOnClickListener{
            onUpdateClicked(item)
        }

        holder.deleteButton.setOnClickListener{
            onDeleteClicked(item)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Logs>() {
            override fun areItemsTheSame(oldItem: Logs, newItem: Logs): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Logs, newItem: Logs): Boolean {
                return oldItem == newItem
            }
        }
    }
}