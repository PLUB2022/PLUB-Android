package com.plub.presentation.ui.main.gathering.create.question.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.plub.presentation.ui.main.gathering.create.question.CreateGatheringQuestion
import com.plub.presentation.databinding.LayoutRecyclerCreateGatheringQuestionBinding

class QuestionRecyclerViewAdapter(
    val onClickDeleteButton: (position: Int) -> Unit,
    val updateEditText: (data: CreateGatheringQuestion, text: String) -> Unit
) : ListAdapter<CreateGatheringQuestion, QuestionViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = LayoutRecyclerCreateGatheringQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding, onClickDeleteButton, updateEditText)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<CreateGatheringQuestion>() {
            override fun areItemsTheSame(
                oldItem: CreateGatheringQuestion,
                newItem: CreateGatheringQuestion
            ): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(
                oldItem: CreateGatheringQuestion,
                newItem: CreateGatheringQuestion
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}