package com.plub.presentation.ui.main.plubing.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.TodoItemViewType
import com.plub.domain.model.enums.TodoTimelineViewType
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.databinding.IncludeItemTodoBinding
import com.plub.presentation.databinding.IncludeItemTodoTimelineBinding
import com.plub.presentation.databinding.IncludeItemTodoTimelineDateBinding

class PlubingTodoAdapter(
    private val listener: Delegate,
) : ListAdapter<TodoTimelineVo, RecyclerView.ViewHolder>(PlubingTodoDiffCallback()) {

    interface Delegate {
        fun onClickTodoChecked(timelineId: Int, vo: TodoItemVo)
        fun onClickTodoMenu(vo: TodoTimelineVo)
        fun onClickTodoLike(timelineId: Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlubingTodoTimelineViewHolder -> holder.bind(currentList[position])
            is PlubingTodoTimelineDateViewHolder -> holder.bind(currentList[position])
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            when (holder) {
                is PlubingTodoTimelineViewHolder -> holder.updateList(currentList[position], payloads.first())
            }
        } else super.onBindViewHolder(holder, position, payloads)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (TodoTimelineViewType.indexOf(viewType)) {
            TodoTimelineViewType.PLUBING -> {
                val binding = IncludeItemTodoTimelineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubingTodoTimelineViewHolder(binding, listener)
            }

            TodoTimelineViewType.DATE -> {
                val binding = IncludeItemTodoTimelineDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubingTodoTimelineDateViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.idx
    }
}

class PlubingTodoDiffCallback : DiffUtil.ItemCallback<TodoTimelineVo>() {
    override fun areItemsTheSame(oldItem: TodoTimelineVo, newItem: TodoTimelineVo): Boolean =
        oldItem.timelineId == newItem.timelineId && oldItem.viewType == newItem.viewType

    override fun areContentsTheSame(oldItem: TodoTimelineVo, newItem: TodoTimelineVo): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: TodoTimelineVo, newItem: TodoTimelineVo): Any? {
        return if (oldItem.todoList != newItem.todoList) newItem.todoList else null
    }
}