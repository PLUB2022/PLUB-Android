package com.plub.presentation.ui.main.plubing.schedule.adapter.scheduleCard

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.R
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleYearBinding
import com.plub.presentation.util.TimeFormatter

class PlubingScheduleYearViewHolder(
    private val binding: LayoutRecyclerPlubingScheduleYearBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(year: String) {

        binding.apply {
            textViewYear.text = binding.root.context.getString(
                R.string.word_birth_year,
                TimeFormatter.getIntYearFromyyyyDashmmDashddFormat(year).toString()
            )
        }
    }
}