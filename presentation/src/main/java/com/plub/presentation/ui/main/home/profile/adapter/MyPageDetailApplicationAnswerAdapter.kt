package com.plub.presentation.ui.main.home.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitDetailVo.host.AnswersVo
import com.plub.presentation.databinding.IncludeItemApplicationsAnswerBinding
import com.plub.presentation.ui.main.home.profile.viewHolder.detail.MyPageDetailApplicationAnswerViewHolder

class MyPageDetailApplicationAnswerAdapter: ListAdapter<AnswersVo, RecyclerView.ViewHolder>(
    MyPageDetailApplicationAnswerDiffCallback()
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyPageDetailApplicationAnswerViewHolder -> holder.bind(currentList[position], position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IncludeItemApplicationsAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPageDetailApplicationAnswerViewHolder(binding)
    }

}

class MyPageDetailApplicationAnswerDiffCallback : DiffUtil.ItemCallback<AnswersVo>() {
    override fun areItemsTheSame(oldItem: AnswersVo, newItem: AnswersVo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AnswersVo, newItem: AnswersVo): Boolean =
        oldItem == newItem
}