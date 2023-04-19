package com.plub.presentation.ui.main.home.recruitment.apply.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.ApplyRecruitQuestionViewType
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo
import com.plub.presentation.databinding.IncludeItemFirstViewApplyRecruitBinding
import com.plub.presentation.databinding.IncludeItemQuestionBinding
import com.plub.presentation.ui.main.home.recruitment.apply.viewholder.QuestionsFirstViewHolder
import com.plub.presentation.ui.main.home.recruitment.apply.viewholder.QuestionsViewHolder

class QuestionsAdapter(private val listener : QuestionsDelegate) :
    ListAdapter<QuestionsDataVo, RecyclerView.ViewHolder>(
        QuestionsDiffCallBack()
    ) {

    interface QuestionsDelegate{
        fun textChanged(questionId : Int, changedText : String)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is QuestionsViewHolder -> {
                holder.bind(currentList[position], position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(ApplyRecruitQuestionViewType.valueOf(viewType)){
            ApplyRecruitQuestionViewType.FIRST -> {
                val binding = IncludeItemFirstViewApplyRecruitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                QuestionsFirstViewHolder(binding)
            }
            ApplyRecruitQuestionViewType.QUESTION -> {
                val binding =
                    IncludeItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                QuestionsViewHolder(binding, listener)
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