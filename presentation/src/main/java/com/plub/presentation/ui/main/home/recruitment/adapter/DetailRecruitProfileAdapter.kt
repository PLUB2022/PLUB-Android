package com.plub.presentation.ui.main.home.recruitment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailJoinedAccountsVo
import com.plub.presentation.databinding.IncludeItemCircleProfileBinding
import com.plub.presentation.ui.main.home.recruitment.viewholder.DetailRecruitProfileViewHolder

class DetailRecruitProfileAdapter(private val listener: DetailProfileDelegate) :
    ListAdapter<RecruitDetailJoinedAccountsVo, RecyclerView.ViewHolder>(
        DetailRecruitProfileDiffCallBack()
    ) {

    private var maxProfile = 0

    interface DetailProfileDelegate {
        fun onProfileClick(accountId: Int, nickname : String)
        fun onSeeMoreProfileClick()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DetailRecruitProfileViewHolder -> {
                holder.bind(currentList[position], position, currentList.size, maxProfile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemCircleProfileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DetailRecruitProfileViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        if (currentList.size >= maxProfile) {
            return maxProfile
        } else {
            return currentList.size
        }
    }

    fun setMaxProfile(num : Int){
        maxProfile = num
    }

}

class DetailRecruitProfileDiffCallBack :
    DiffUtil.ItemCallback<RecruitDetailJoinedAccountsVo>() {
    override fun areItemsTheSame(
        oldItem: RecruitDetailJoinedAccountsVo,
        newItem: RecruitDetailJoinedAccountsVo
    ): Boolean = oldItem.accountId == newItem.accountId

    override fun areContentsTheSame(
        oldItem: RecruitDetailJoinedAccountsVo,
        newItem: RecruitDetailJoinedAccountsVo
    ): Boolean = oldItem == newItem
}