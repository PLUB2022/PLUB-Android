package com.plub.presentation.ui.main.plubing.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.TodoItemViewType
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.presentation.databinding.IncludeItemTodoBinding
import com.plub.presentation.databinding.IncludeItemTodoDetailBinding
import com.plub.presentation.databinding.IncludeItemTodoPlannerBinding

class TodoItemAdapter(
    private val listener: Delegate,
) : ListAdapter<TodoItemVo, RecyclerView.ViewHolder>(TodoDiffCallback()) {

    interface Delegate {
        fun onClickTodoContent()
        fun onClickTodoCheck(todoItemVo: TodoItemVo)
        fun onClickTodoMenu(todoItemVo: TodoItemVo)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodoItemViewHolder -> holder.bind(currentList[position])
            is TodoItemPlannerViewHolder -> holder.bind(currentList[position])
            is TodoItemDetailViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (TodoItemViewType.indexOf(viewType)) {
            TodoItemViewType.PLUBING,
            TodoItemViewType.PROFILE -> {
                val binding = IncludeItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TodoItemViewHolder(binding, listener)
            }
            TodoItemViewType.PLANNER -> {
                val binding = IncludeItemTodoPlannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TodoItemPlannerViewHolder(binding, listener)
            }
            TodoItemViewType.DETAIL -> {
                val binding = IncludeItemTodoDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TodoItemDetailViewHolder(binding, listener)
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