package com.plub.presentation.ui.main.home.recruitment.apply.viewholder

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemQuestionBinding
import com.plub.presentation.ui.main.home.recruitment.apply.adapter.QuestionsAdapter

class QuestionsViewHolder(
    private val binding: IncludeItemQuestionBinding,
    private val listener : QuestionsAdapter.QuestionsDegelate
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.apply {
            editTextAnswer.addTextChangedListener {
                textViewNowText.text = editTextAnswer.text.length.toString()
                listener.isNotEmpty()
            }
        }
    }

    fun bind(item: QuestionsDataVo, number : Int) {
        binding.apply {
            textViewQuestion.text = root.context.getString(R.string.apply_plubbing_question, number, item.question)
        }
    }

}