package com.plub.presentation.ui.main.gathering.createGathering.question.adapter

import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.databinding.LayoutRecyclerCreateGatheringQuestionBinding
import com.plub.presentation.ui.main.gathering.createGathering.question.CreateGatheringQuestion
import com.plub.presentation.util.afterTextChanged
import com.plub.presentation.util.onThrottleClick
import com.plub.presentation.util.requestAndClearFocus

class QuestionViewHolder(
    private val binding: LayoutRecyclerCreateGatheringQuestionBinding,
    private val onClickDeleteButton: (position: Int) -> Unit,
    private val updateEditText: (data: CreateGatheringQuestion, text: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private var data: CreateGatheringQuestion? = null
    init {
        binding.editTextQuestion.afterTextChanged { text ->
            data?.let { data ->
                updateEditText(data, text.toString())
            }
        }

        binding.imageViewDelete.onThrottleClick {
            /**
             * recyclerView의 animation을 disable한 상태에서
             * editText가 focus된 상태로 recyclerView의 아이템이 갱신되면
             * recyclerView가 invisible되는 현상이 있어 임시로 textView에 focus를 주고 clear하는 방식으로 해결함
             * 단순히 editText에 clearFocus를 하지 않은 이유는 해당 recyclerView item의 editText가 아닌
             * 다른 recyclerView item의 editText에 focus가 되어있을 수 있기 때문
             */
            data?.let { data ->
                binding.textViewTitle.requestAndClearFocus()
                onClickDeleteButton(data.position)
            }
        }

        binding.executePendingBindings()
    }

    fun bind(data: CreateGatheringQuestion) {
        binding.data = data
        this.data = data
    }
}