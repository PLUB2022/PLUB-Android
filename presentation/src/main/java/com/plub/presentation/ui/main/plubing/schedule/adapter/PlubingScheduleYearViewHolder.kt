package com.plub.presentation.ui.main.plubing.schedule.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleYearBinding

class PlubingScheduleYearViewHolder(
    private val binding: LayoutRecyclerPlubingScheduleYearBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(year: String) {

        binding.apply {
            textViewYear.text = year
        }
    }
}