package com.plub.presentation.ui.main.profile.bottomsheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.OtherProfileBottomSheetViewType
import com.plub.domain.model.vo.myPage.OtherProfileVo
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoTimelineVo
import com.plub.presentation.databinding.*
import com.plub.presentation.ui.main.profile.active.adapter.MyPageTodoTimeLineAdapter
import com.plub.presentation.ui.main.profile.active.adapter.MyPageTodoTimeLineViewHolder

class OtherProfileAdapter(private val listener: TodoDelegate): ListAdapter<OtherProfileVo, RecyclerView.ViewHolder>(
    OtherProfileDiffCallback()
) {

    interface TodoDelegate : MyPageTodoTimeLineAdapter.MyPageTodoDelegate {
        override fun onClickTodoChecked(timelineId: Int, vo: TodoItemVo)
        override fun onClickTodoMenu(vo: TodoTimelineVo)
        override fun onClickTodoLike(timelineId: Int)
        override fun onClickTimeline(timelineId: Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OtherProfileDataViewHolder -> holder.bind(currentList[position].info)
            is MyPageTodoTimeLineViewHolder -> holder.bind(currentList[position].todoList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(OtherProfileBottomSheetViewType.valueOf(viewType)){
            OtherProfileBottomSheetViewType.PROFILE -> {
                val binding = IncludeItemOtherProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                OtherProfileDataViewHolder(binding)
            }
            OtherProfileBottomSheetViewType.TODO -> {
                val binding = IncludeItemTodoTimelineWithLineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyPageTodoTimeLineViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }

}

class OtherProfileDiffCallback : DiffUtil.ItemCallback<OtherProfileVo>() {
    override fun areItemsTheSame(oldItem: OtherProfileVo, newItem: OtherProfileVo): Boolean =
        oldItem.viewType == newItem.viewType

    override fun areContentsTheSame(oldItem: OtherProfileVo, newItem: OtherProfileVo): Boolean =
        oldItem == newItem
}