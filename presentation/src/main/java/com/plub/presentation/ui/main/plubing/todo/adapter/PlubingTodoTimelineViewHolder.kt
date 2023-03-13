package com.plub.presentation.ui.main.plubing.todo.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.databinding.IncludeItemTodoTimelineBinding
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.onThrottleClick

class PlubingTodoTimelineViewHolder(
    private val binding: IncludeItemTodoTimelineBinding,
    private val listener: PlubingTodoAdapter.Delegate
) : RecyclerView.ViewHolder(binding.root) {

    private var vo: TodoTimelineVo? = null

    private val todoItemAdapter: TodoItemAdapter =
        TodoItemAdapter(object : TodoItemAdapter.Delegate {
            override fun onClickTodoCheck(todoItemVo: TodoItemVo) {
                vo?.let {
                    listener.onClickTodoChecked(it.timelineId, todoItemVo)
                }
            }
        })

    init {
        binding.apply {
            recyclerViewTodo.apply {
                adapter = todoItemAdapter
                layoutManager = LinearLayoutManager(root.context)
            }

            imageViewMenu.onThrottleClick {
                vo?.let {
                    listener.onClickTodoMenu(it)
                }
            }
        }
    }

    fun bind(item: TodoTimelineVo) {
        vo = item
        binding.apply {
            textViewLikeCount.text = item.totalLikes.toString()
            todoItemAdapter.submitList(item.todoList)
            GlideUtil.loadImage(root.context, item.accountInfoVo.profileImage, circleImageViewProfile)
        }
    }

    fun updateList(item: TodoTimelineVo, payload: Any) {
        vo = item
        val todoList = payload as List<TodoItemVo>
        todoItemAdapter.submitList(todoList)
    }
}