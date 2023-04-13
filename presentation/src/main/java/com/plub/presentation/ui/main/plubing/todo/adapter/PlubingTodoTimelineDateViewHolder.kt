package com.plub.presentation.ui.main.plubing.todo.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.databinding.IncludeItemTodoTimelineDateBinding

class PlubingTodoTimelineDateViewHolder(
    private val binding: IncludeItemTodoTimelineDateBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: TodoTimelineVo? = null

    fun bind(item: TodoTimelineVo) {
        vo = item
        binding.apply {
            textViewDate.text = item.date
        }
    }
}