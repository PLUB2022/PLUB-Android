package com.plub.presentation.ui.main.gathering.create.question.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.plub.presentation.databinding.BottomSheetDeleteQuestionBinding
import com.plub.presentation.util.onThrottleClick
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BottomSheetDeleteQuestion : BottomSheetDialogFragment() {
    private val binding: BottomSheetDeleteQuestionBinding by lazy {
        BottomSheetDeleteQuestionBinding.inflate(layoutInflater)
    }

    companion object {
        private const val POSITION = "POSITION"
        private const val QUESTION_COUNT = "QUESTION_COUNT"

        fun newInstance(
            position: Int,
            questionCount: Int,
            okButtonClickEvent: ((position: Int) -> Unit)? = null
        ) = BottomSheetDeleteQuestion().apply {
            this.okButtonClickEvent = okButtonClickEvent
            arguments = Bundle().apply {
                putInt(POSITION, position)
                putInt(QUESTION_COUNT, questionCount)
            }
        }
    }

    private val position: Int by lazy { arguments?.getInt(POSITION) ?: 0 }
    private val questionCount: Int by lazy { arguments?.getInt(QUESTION_COUNT) ?: 0 }
    private var okButtonClickEvent: ((position: Int) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.position = position
        binding.questionCount = questionCount

        binding.buttonYes.onThrottleClick {
            okButtonClickEvent?.let { it(position) }
            dismiss()
        }

        binding.buttonNo.onThrottleClick {
            dismiss()
        }
    }
}