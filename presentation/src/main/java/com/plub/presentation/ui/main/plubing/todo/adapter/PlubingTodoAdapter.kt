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
import com.plub.presentation.databinding.*
import com.plub.presentation.ui.main.home.progress.LoadingViewHolder

class PlubingTodoAdapter(
    private val listener: Delegate,
) : ListAdapter<TodoTimelineVo, RecyclerView.ViewHolder>(PlubingTodoDiffCallback()) {

    interface Delegate {
        fun onClickTodoChecked(timelineId: Int, vo: TodoItemVo)
        fun onClickTodoMenu(vo: TodoTimelineVo)
        fun onClickTodoLike(timelineId: Int)
        fun onClickTimeline(timelineId: Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlubingTodoTimelineViewHolder -> holder.bind(currentList[position])
            is PlubingTodoTimelineDateViewHolder -> holder.bind(currentList[position])
            is PlubingTodoTimelineGoalViewHolder -> holder.bind()
        }
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

            TodoTimelineViewType.GOAL -> {
                val binding = IncludeItemTodoTimelineGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PlubingTodoTimelineGoalViewHolder(binding)
            }
            TodoTimelineViewType.LOADING -> {
                val binding = IncludeItemProgressBarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(binding)
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
}