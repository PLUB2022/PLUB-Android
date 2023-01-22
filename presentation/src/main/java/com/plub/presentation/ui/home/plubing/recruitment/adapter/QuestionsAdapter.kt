package com.plub.presentation.ui.home.plubing.recruitment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo
import com.plub.presentation.databinding.IncludeItemQuestionBinding

class QuestionsAdapter() :
    ListAdapter<QuestionsDataVo, RecyclerView.ViewHolder>(
        QuestionsDiffCallBack()
    ) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is QuestionsViewHolder -> {
                holder.bind(currentList[position], position + 1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            IncludeItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionsViewHolder(binding)
    }
}

class QuestionsDiffCallBack : DiffUtil.ItemCallback<QuestionsDataVo>() {
    override fun areItemsTheSame(oldItem: QuestionsDataVo, newItem: QuestionsDataVo): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: QuestionsDataVo, newItem: QuestionsDataVo): Boolean =
        oldItem == newItem
}