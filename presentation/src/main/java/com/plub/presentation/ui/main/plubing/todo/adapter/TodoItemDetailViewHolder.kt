package com.plub.presentation.ui.main.plubing.todo.adapter

import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemTodoDetailBinding
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick

class TodoItemDetailViewHolder(
    private val binding: IncludeItemTodoDetailBinding,
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
            val textColorRes = if (item.isChecked) R.color.color_8c8c8c else R.color.black
            val checkRes = if (item.isChecked) R.drawable.ic_checked_box_5f5ff9 else R.drawable.ic_box_empty_f2f3f4
            imageViewProof.visibility = if(item.isProof) View.VISIBLE else View.GONE
            imageViewCheck.setImageResource(checkRes)
            textViewContent.apply {
                val textPaintFlag = if (item.isChecked) paintFlags or (Paint.STRIKE_THRU_TEXT_FLAG) else paintFlags and (Paint.STRIKE_THRU_TEXT_FLAG.inv())
                setTextColor(ContextCompat.getColor(root.context, textColorRes))
                paintFlags = textPaintFlag
                text = item.content
            }
            GlideUtil.loadImage(root.context, item.proofImage, imageViewProof)
        }
    }
}