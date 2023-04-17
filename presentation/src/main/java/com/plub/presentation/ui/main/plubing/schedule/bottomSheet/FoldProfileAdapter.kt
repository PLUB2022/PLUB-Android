package com.plub.presentation.ui.main.plubing.schedule.bottomSheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.IncludeItemCircleProfileBinding
import com.plub.presentation.databinding.LayoutRecyclerScheduleDetailFoldProfileBinding
import com.plub.presentation.ui.main.home.recruitment.viewholder.DetailRecruitProfileViewHolder

class FoldProfileAdapter(private val maxProfile: Int) : ListAdapter<String, RecyclerView.ViewHolder>(
    ProfileDiffCallBack()
) {



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FoldProfileViewHolder -> {
                holder.bind(currentList[position], position, currentList.size, maxProfile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutRecyclerScheduleDetailFoldProfileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FoldProfileViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (currentList.size >= maxProfile) {
            maxProfile
        } else {
            currentList.size
        }
    }

}

class ProfileDiffCallBack :
    DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean = false

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean = false
}