package com.plub.presentation.ui.main.home.recruitment.apply.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.QuestionDataType
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo
import com.plub.presentation.databinding.IncludeItemFirstViewApplyRecruitBinding
import com.plub.presentation.databinding.IncludeItemQuestionBinding
import com.plub.presentation.ui.main.home.recruitment.apply.viewholder.QuestionsFirstViewHolder
import com.plub.presentation.ui.main.home.recruitment.apply.viewholder.QuestionsViewHolder

class QuestionsAdapter(private val listener : QuestionsDegelate) :
    ListAdapter<QuestionsDataVo, RecyclerView.ViewHolder>(
        QuestionsDiffCallBack()
    ) {

    interface QuestionsDegelate{
        fun isNotEmpty(flag : Boolean)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is QuestionsViewHolder -> {
                holder.bind(currentList[position], position + 1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(QuestionDataType.valueOf(viewType)){
            QuestionDataType.FIRST -> {
                val binding = IncludeItemFirstViewApplyRecruitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return QuestionsFirstViewHolder(binding)
            }
            QuestionDataType.DATA -> {
                val binding =
                    IncludeItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return QuestionsViewHolder(binding, listener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].viewType.type
    }
}

class QuestionsDiffCallBack : DiffUtil.ItemCallback<QuestionsDataVo>() {
    override fun areItemsTheSame(oldItem: QuestionsDataVo, newItem: QuestionsDataVo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: QuestionsDataVo, newItem: QuestionsDataVo): Boolean =
        oldItem == newItem
}