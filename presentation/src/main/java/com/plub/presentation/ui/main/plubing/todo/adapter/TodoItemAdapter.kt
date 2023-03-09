package com.plub.presentation.ui.main.plubing.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.TodoItemViewType
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.presentation.databinding.IncludeItemTodoBinding

class TodoItemAdapter(
    private val listener: Delegate,
) : ListAdapter<TodoItemVo, RecyclerView.ViewHolder>(TodoDiffCallback()) {

    interface Delegate {
        fun onClickTodoCheck(todoItemVo: TodoItemVo)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlubingTodoItemViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (TodoItemViewType.indexOf(viewType)) {
            TodoItemViewType.PLUBING,
            TodoItemViewType.MANAGING,
            TodoItemViewType.DETAIL,
            TodoItemViewType.PROFILE -> {
                val binding = IncludeItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubingTodoItemViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.idx
    }
}

class TodoDiffCallback : DiffUtil.ItemCallback<TodoItemVo>() {
    override fun areItemsTheSame(oldItem: TodoItemVo, newItem: TodoItemVo): Boolean =
        oldItem.todoId == newItem.todoId && oldItem.viewType == newItem.viewType

    override fun areContentsTheSame(oldItem: TodoItemVo, newItem: TodoItemVo): Boolean =
        oldItem == newItem
}