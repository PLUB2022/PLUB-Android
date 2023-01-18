package com.plub.presentation.ui.createGathering.question.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.ui.createGathering.question.CreateGatheringQuestion
import com.plub.presentation.databinding.LayoutRecyclerCreateGatheringQuestionBinding
import com.plub.presentation.util.afterTextChanged
import com.plub.presentation.util.requestAndClearFocus

class QuestionRecyclerViewAdapter(
    val onClickDeleteButton: (position: Int) -> Unit,
    val updateEditText: (data: CreateGatheringQuestion, text: String) -> Unit
) :
    ListAdapter<CreateGatheringQuestion, QuestionRecyclerViewAdapter.QuestionViewHolder>(
        diffUtil
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = LayoutRecyclerCreateGatheringQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class QuestionViewHolder(private val binding: LayoutRecyclerCreateGatheringQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CreateGatheringQuestion) {
            binding.data = data
            binding.editTextQuestion.afterTextChanged { text ->
                updateEditText(data, text.toString())
            }

            binding.imageViewDelete.setOnClickListener {
                /**
                 * recyclerView의 animation을 disable한 상태로
                 * editText가 focus된 상태로 recyclerView의 아이템이 갱신되면
                 * recyclerView가 invisible되는 현상이 있어 임시로 textView에 focus를 주고 clear하는 방식으로 해결함
                 * 단순히 editText에 clearFocus를 하지 않은 이유는 해당 recyclerView item의 editText가 아닌
                 * 다른 recyclerView item의 editText에 focus가 되어있을 수 있기 때문
                 */
                binding.textViewTitle.requestAndClearFocus()
                onClickDeleteButton(data.position)
            }
            binding.executePendingBindings()
        }
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