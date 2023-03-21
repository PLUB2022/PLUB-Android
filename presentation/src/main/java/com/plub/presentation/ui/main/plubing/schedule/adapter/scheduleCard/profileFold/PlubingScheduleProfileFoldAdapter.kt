package com.plub.presentation.ui.main.plubing.schedule.adapter.scheduleCard.profileFold

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleProfileBinding

class PlubingScheduleProfileAdapter() :
    ListAdapter<String, RecyclerView.ViewHolder>(
        PlubingScheduleProfileDiffCallBack()
    ) {

    companion object {
        private const val MAX_PROFILE_SIZE = 4
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlubingScheduleProfileFoldViewHolder -> {
                holder.bind(currentList[position], position, currentList.size)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutRecyclerPlubingScheduleProfileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlubingScheduleProfileFoldViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (currentList.size >= MAX_PROFILE_SIZE) {
            MAX_PROFILE_SIZE
        } else {
            currentList.size
        }
    }

}

class PlubingScheduleProfileDiffCallBack :
    DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean = oldItem == newItem
}