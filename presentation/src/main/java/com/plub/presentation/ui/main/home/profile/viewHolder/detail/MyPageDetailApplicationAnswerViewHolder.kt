package com.plub.presentation.ui.main.home.profile.viewHolder.detail

import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.recruitdetailvo.host.AnswersVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemApplicationsAnswerBinding

class MyPageDetailApplicationAnswerViewHolder(
    private val binding: IncludeItemApplicationsAnswerBinding,
) : RecyclerView.ViewHolder(binding.root) {
    init {
    }

    fun bind(item : AnswersVo, position : Int) {
        val number = position + 1
        binding.apply {
            textViewQuestion.text = root.context.getString(R.string.apply_plubbing_question, number, item.questions)
            textViewAnswer.text = item.answer
        }
    }
}