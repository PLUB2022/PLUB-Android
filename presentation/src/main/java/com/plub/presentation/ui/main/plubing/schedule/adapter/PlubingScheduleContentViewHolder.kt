package com.plub.presentation.ui.main.plubing.schedule.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.domain.model.vo.schedule.ScheduleVo
import com.plub.presentation.databinding.LayoutRecyclerPlubingScheduleContentBinding

class PlubingScheduleContentViewHolder(
    private val binding: LayoutRecyclerPlubingScheduleContentBinding,
    private val onClick: (() -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClick?.invoke()
        }
    }

    fun bind(item: ScheduleVo) {

        binding.apply {
            textViewTitle.text = item.title
        }
    }
}