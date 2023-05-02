package com.plub.presentation.ui.main.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.MyPageDetailViewType
import com.plub.domain.model.vo.myPage.MyPageDetailVo
import com.plub.presentation.databinding.*
import com.plub.presentation.ui.main.profile.viewHolder.detail.MyPageDetailTopViewHolder
import com.plub.presentation.ui.main.profile.recruiting.viewHolder.MyPageDetailEmptyViewHolder
import com.plub.presentation.ui.main.profile.recruiting.viewHolder.MyPageDetailOtherApplicantsViewHolder
import com.plub.presentation.ui.main.profile.recruiting.viewHolder.MyPageDetailOtherApplicationTextViewHolder
import com.plub.presentation.ui.main.profile.waiting.viewHolder.MyPageDetailMyApplicantsViewHolder
import com.plub.presentation.ui.main.profile.waiting.viewHolder.MyPageDetailMyApplicationsTextViewHolder

class MyPageDetailPageAdapter(private val listener : ApplicantsDelegate): ListAdapter<MyPageDetailVo, RecyclerView.ViewHolder>(
    MyPageDetailDiffCallback()
) {

    interface ApplicantsDelegate{
        fun onClickApproveButton(accountId : Int)
        fun onClickRejectButton(accountId : Int)
        fun onClickCancelButton()
        fun onClickModifyButton()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyPageDetailTopViewHolder -> holder.bind(currentList[position].title, false)
            is MyPageDetailMyApplicationsTextViewHolder -> holder.bind()
            is MyPageDetailOtherApplicationTextViewHolder -> holder.bind()
            is MyPageDetailOtherApplicantsViewHolder -> holder.bind(currentList[position].application)
            is MyPageDetailMyApplicantsViewHolder -> holder.bind(currentList[position].application)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(MyPageDetailViewType.valueOf(viewType)){
            MyPageDetailViewType.TOP -> {
                val binding = IncludeItemMyPageTopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyPageDetailTopViewHolder(binding)
            }
            MyPageDetailViewType.MY_APPLICANTS_TEXT -> {
                val binding = IncludeItemMyPageMyApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyPageDetailMyApplicationsTextViewHolder(binding)
            }
            MyPageDetailViewType.OTHER_APPLICANTS_TEXT -> {
                val binding = IncludeItemMyPageOtherApplicationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyPageDetailOtherApplicationTextViewHolder(binding)
            }
            MyPageDetailViewType.MY_APPLICATION -> {
                val binding = IncludeItemMyApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyPageDetailMyApplicantsViewHolder(binding, listener)
            }
            MyPageDetailViewType.OTHER_APPLICATION -> {
                val binding = IncludeItemApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyPageDetailOtherApplicantsViewHolder(binding, listener)
            }
            MyPageDetailViewType.EMPTY -> {
                val binding = IncludeItemMyPageNoOtherApplicationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyPageDetailEmptyViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }
}

class MyPageDetailDiffCallback : DiffUtil.ItemCallback<MyPageDetailVo>() {
    override fun areItemsTheSame(oldItem: MyPageDetailVo, newItem: MyPageDetailVo): Boolean =
        oldItem.viewType == newItem.viewType

    override fun areContentsTheSame(oldItem: MyPageDetailVo, newItem: MyPageDetailVo): Boolean =
        oldItem == newItem
}