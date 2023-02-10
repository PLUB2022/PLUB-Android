package com.plub.presentation.ui.main.home.recruitment.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailJoinedAccountsListVo
import com.plub.presentation.databinding.IncludeItemCircleProfileDetailBinding

class BottomSheetProfileAdapter(private val listener: ProfileDelegate) :
    ListAdapter<RecruitDetailJoinedAccountsListVo, RecyclerView.ViewHolder>(
        DetailRecruitProfileDiffCallBack()
    ) {

    interface ProfileDelegate {
        fun onProfileClick(accountId: Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BottomSheetProfileViewHolder -> {
                holder.bind(currentList[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemCircleProfileDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BottomSheetProfileViewHolder(binding, listener)
    }

}

class DetailRecruitProfileDiffCallBack :
    DiffUtil.ItemCallback<RecruitDetailJoinedAccountsListVo>() {
    override fun areItemsTheSame(
        oldItem: RecruitDetailJoinedAccountsListVo,
        newItem: RecruitDetailJoinedAccountsListVo
    ): Boolean = oldItem.accountId == newItem.accountId

    override fun areContentsTheSame(
        oldItem: RecruitDetailJoinedAccountsListVo,
        newItem: RecruitDetailJoinedAccountsListVo
    ): Boolean = oldItem == newItem
}