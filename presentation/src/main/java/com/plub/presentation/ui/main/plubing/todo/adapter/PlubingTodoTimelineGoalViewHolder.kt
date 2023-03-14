package com.plub.presentation.ui.main.plubing.todo.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemTodoTimelineGoalBinding
import com.plub.presentation.util.PlubingInfo

class PlubingTodoTimelineGoalViewHolder(
    private val binding: IncludeItemTodoTimelineGoalBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind() {
        binding.apply {
            val goal = root.context.getString(R.string.word_double_quotation_marks, PlubingInfo.info.goal)
            textViewGoal.text = goal
        }
    }
}