package com.plub.presentation.ui.main.profile.active.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.TodoTimelineViewType
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.databinding.*
import com.plub.presentation.ui.main.home.progress.LoadingViewHolder

class MyPageTodoTimeLineAdapter(
    private val listener: MyPageTodoDelegate,
) : ListAdapter<TodoTimelineVo, RecyclerView.ViewHolder>(MyPageTodoDiffCallback()) {

    interface MyPageTodoDelegate {
        fun onClickTodoChecked(timelineId: Int, vo: TodoItemVo)
        fun onClickTodoMenu(vo: TodoTimelineVo)
        fun onClickTodoLike(timelineId: Int)
        fun onClickTimeline(timelineId: Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyPageTodoTimeLineViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (TodoTimelineViewType.indexOf(viewType)) {
            TodoTimelineViewType.LOADING -> {
                val binding = IncludeItemProgressBarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(binding)
            }
            else -> {
                val binding = IncludeItemTodoTimelineWithLineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyPageTodoTimeLineViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.idx
    }
}

class MyPageTodoDiffCallback : DiffUtil.ItemCallback<TodoTimelineVo>() {
    override fun areItemsTheSame(oldItem: TodoTimelineVo, newItem: TodoTimelineVo): Boolean =
        oldItem.timelineId == newItem.timelineId

    override fun areContentsTheSame(oldItem: TodoTimelineVo, newItem: TodoTimelineVo): Boolean =
        oldItem == newItem
}