package com.plub.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailJoinedAccountsListVo
import com.plub.presentation.databinding.IncludeItemCircleProfileBinding
import com.plub.presentation.ui.home.adapter.viewholder.DetailRecruitProfileViewHolder

class DetailRecruitProfileAdapter(private val listener : DetailProfileDegelate) : ListAdapter<RecruitDetailJoinedAccountsListVo, RecyclerView.ViewHolder>(
    DetailRecruitProfileDiffCallBack()
){
    interface DetailProfileDegelate{
        fun onProfileClick(accountId : Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DetailRecruitProfileViewHolder -> {
                if (position < 8){
                    holder.bind(currentList[position], position + 1)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemCircleProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailRecruitProfileViewHolder(binding, listener)
    }

}

class DetailRecruitProfileDiffCallBack : DiffUtil.ItemCallback<RecruitDetailJoinedAccountsListVo>() {
    override fun areItemsTheSame(oldItem: RecruitDetailJoinedAccountsListVo, newItem: RecruitDetailJoinedAccountsListVo): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: RecruitDetailJoinedAccountsListVo, newItem: RecruitDetailJoinedAccountsListVo): Boolean = oldItem == newItem
}