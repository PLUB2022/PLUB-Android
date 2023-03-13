package com.plub.presentation.ui.main.plubing.todo.adapter

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemTodoPlannerBinding
import com.plub.presentation.util.onThrottleClick

class TodoItemPlannerViewHolder(
    private val binding: IncludeItemTodoPlannerBinding,
    private val listener: TodoItemAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: TodoItemVo? = null

    init {
        binding.imageViewCheck.onThrottleClick {
            vo?.let {
                listener.onClickTodoCheck(it)
            }
        }

        binding.imageViewMenu.onThrottleClick {
            vo?.let {
                listener.onClickTodoMenu(it)
            }
        }
    }

    fun bind(item: TodoItemVo) {
        vo = item
        binding.apply {
            val checkRes = if(item.isChecked) R.drawable.ic_checked_box_5f5ff9 else R.drawable.ic_uncheck_box_e1e1fa
            imageViewCheck.setImageResource(checkRes)
            textViewContent.apply {
                val textPaintFlag = if (item.isChecked) paintFlags or (Paint.STRIKE_THRU_TEXT_FLAG) else paintFlags and (Paint.STRIKE_THRU_TEXT_FLAG.inv())
                paintFlags = textPaintFlag
                text = item.content
            }
        }
    }
}