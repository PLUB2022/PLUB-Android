package com.plub.presentation.ui.main.home.recruitment.apply.viewholder

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo
import com.plub.presentation.R
import com.plub.presentation.databinding.IncludeItemQuestionBinding
import com.plub.presentation.ui.main.home.recruitment.apply.adapter.QuestionsAdapter

class QuestionsViewHolder(
    private val binding: IncludeItemQuestionBinding,
    private val listener : QuestionsAdapter.QuestionsDelegate
) : RecyclerView.ViewHolder(binding.root) {
    private var itemVo : QuestionsDataVo? = null

    init {
        binding.apply {
            editTextAnswer.addTextChangedListener {
                textViewNowText.text = editTextAnswer.text.length.toString()
                itemVo?.let { listener.textChanged(it.id, editTextAnswer.text.toString())}
            }
        }
    }

    fun bind(item: QuestionsDataVo, number : Int) {
        itemVo = item
        binding.apply {
            textViewQuestion.text = root.context.getString(R.string.apply_plubbing_question, number, item.question)
            editTextAnswer.setText(item.answer)
            textViewNowText.text = editTextAnswer.text.length.toString()
        }
    }

}