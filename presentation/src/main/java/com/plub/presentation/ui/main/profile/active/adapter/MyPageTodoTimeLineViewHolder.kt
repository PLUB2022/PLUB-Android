package com.plub.presentation.ui.main.profile.active.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemTodoTimelineWithLineBinding
import com.plub.presentation.ui.main.plubing.todo.adapter.TodoItemAdapter
import com.plub.presentation.util.onThrottleClick

class MyPageTodoTimeLineViewHolder(
    private val binding: IncludeItemTodoTimelineWithLineBinding,
    private val listener : MyPageTodoTimeLineAdapter.MyPageTodoDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: TodoTimelineVo? = null

    private val todoItemAdapter: TodoItemAdapter =
        TodoItemAdapter(object : TodoItemAdapter.Delegate {
            override fun onClickTodoCheck(todoItemVo: TodoItemVo) {
                vo?.let {
                    listener.onClickTodoChecked(it.timelineId, todoItemVo)
                }
            }

            override fun onClickTodoContent() {
                vo?.let {
                    listener.onClickTimeline(it.timelineId)
                }
            }

            override fun onClickTodoMenu(todoItemVo: TodoItemVo) {

            }
        })

    init {
        binding.apply {
            recyclerViewTodoItem.apply {
                adapter = todoItemAdapter
                layoutManager = LinearLayoutManager(root.context)
            }

            constraintLayoutTodoCard.onThrottleClick {
                vo?.let {
                    listener.onClickTimeline(it.timelineId)
                }
            }

            imageViewMenuIcon.onThrottleClick {
                vo?.let {
                    listener.onClickTodoMenu(it)
                }
            }

            imageViewLikeIcon.onThrottleClick {
                vo?.let {
                    listener.onClickTodoLike(it.timelineId)
                }
            }
        }
    }

    fun bind(item: TodoTimelineVo) {
        vo = item
        binding.apply {
            val likeIcon = if(item.isLike) R.drawable.ic_heart_no_padding else R.drawable.ic_empty_heart
            imageViewLikeIcon.setImageResource(likeIcon)
            textViewTodoDate.text = item.date
            textViewLikeCount.text = item.totalLikes.toString()
            todoItemAdapter.submitList(item.todoList)
        }
    }
}