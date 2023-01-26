package com.plub.presentation.ui.home.plubing.recruitment.viewholder

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo
import com.plub.presentation.databinding.IncludeItemQuestionBinding
import com.plub.presentation.ui.home.plubing.recruitment.adpater.QuestionsAdapter

class QuestionsViewHolder(
    private val binding: IncludeItemQuestionBinding,
    private val listener: QuestionsAdapter.QuestionsDegelate
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val SPACE = ". "
    }

    init {
        binding.apply {
            editTextAnswer.addTextChangedListener {
                textViewNowText.text = editTextAnswer.text.length.toString()
            }
        }
    }

    fun bind(item: QuestionsDataVo, number : Int) {
        binding.apply {
            textViewQuestion.text = number.toString() + SPACE + item.question
        }
    }

}