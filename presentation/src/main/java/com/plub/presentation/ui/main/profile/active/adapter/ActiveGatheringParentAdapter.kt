package com.plub.presentation.ui.main.profile.active.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MyPageActiveDetailViewType
import com.plub.domain.model.vo.myPage.MyPageActiveDetailVo
import com.plub.presentation.databinding.*
import com.plub.presentation.ui.main.profile.viewHolder.detail.MyPageDetailTopViewHolder
import com.plub.presentation.ui.main.profile.viewHolder.detail.active.MyPageDetailMyPostViewHolder
import com.plub.presentation.ui.main.profile.viewHolder.detail.active.MyPageDetailMyTodoViewHolder

class ActiveGatheringParentAdapter(private val listener : ActiveGatheringDelegate): ListAdapter<MyPageActiveDetailVo, RecyclerView.ViewHolder>(
    MyPageActiveDetailDiffCallback()
) {

    interface ActiveGatheringDelegate{
        fun onClickBoard(feedId: Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyPageDetailTopViewHolder -> holder.bind(currentList[position].title, true)
            is MyPageDetailMyTodoViewHolder -> holder.bind(currentList[position].todoList)
            is MyPageDetailMyPostViewHolder -> holder.bind(currentList[position].postList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(MyPageActiveDetailViewType.valueOf(viewType)){
            MyPageActiveDetailViewType.TOP -> {
                val binding = IncludeItemMyPageTopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyPageDetailTopViewHolder(binding)
            }
            MyPageActiveDetailViewType.MY_TODO -> {
                val binding = IncludeItemMyPageActiveMyTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyPageDetailMyTodoViewHolder(binding)
            }
            MyPageActiveDetailViewType.MY_POST -> {
                val binding = IncludeItemMyPageActiveMyPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyPageDetailMyPostViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }
}

class MyPageActiveDetailDiffCallback : DiffUtil.ItemCallback<MyPageActiveDetailVo>() {
    override fun areItemsTheSame(oldItem: MyPageActiveDetailVo, newItem: MyPageActiveDetailVo): Boolean =
        oldItem.viewType == newItem.viewType

    override fun areContentsTheSame(oldItem: MyPageActiveDetailVo, newItem: MyPageActiveDetailVo): Boolean =
        oldItem == newItem
}