package com.plub.presentation.ui.main.plubing.todo.adapter

import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemTodoBinding
import com.plub.presentation.util.onThrottleClick

class TodoItemViewHolder(
    private val binding: IncludeItemTodoBinding,
    private val listener: TodoItemAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: TodoItemVo? = null

    init {
        binding.imageViewCheck.onThrottleClick {
            vo?.let {
                listener.onClickTodoCheck(it)
            }
        }

        binding.textViewContent.onThrottleClick {
            listener.onClickTodoContent()
        }
    }

    fun bind(item: TodoItemVo) {
        vo = item
        binding.apply {
            val checkRes = if(item.isChecked) R.drawable.ic_checked_12 else R.drawable.ic_unchecked_empty
            imageViewCheck.setImageResource(checkRes)
            textViewContent.apply {
                val textColorRes = if (item.isChecked) R.color.color_8c8c8c else R.color.black
                val textPaintFlag = if (item.isChecked) paintFlags or (Paint.STRIKE_THRU_TEXT_FLAG) else paintFlags and (Paint.STRIKE_THRU_TEXT_FLAG.inv())
                setTextColor(ContextCompat.getColor(root.context, textColorRes))
                paintFlags = textPaintFlag
                text = item.content
            }
        }
    }
}