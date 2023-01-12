package com.plub.presentation.ui.createGathering.question

import com.plub.presentation.state.PageState

data class CreateGatheringQuestionPageState(
    private val _questions: List<CreateGatheringQuestion> = listOf(CreateGatheringQuestion()),
    val isNeedQuestionCheck: Boolean? = null,
    val needUpdateRecyclerView: Boolean = true,
    val isAddQuestionButtonVisible: Boolean = false
) : PageState {
    val questions
        get() = if(isNeedQuestionCheck == true) _questions else emptyList()
    val isNextButtonEnabled =
        when (isNeedQuestionCheck) {
            true -> {
                _questions.find { it.question.isBlank() } == null
            }
            false -> {
                true
            }
            else -> false // null인 경우 (아무 것도 선택 되지 않은 상태)
        }
}

data class CreateGatheringQuestion(
    var key: Int = 0,
    var position: Int = 1,
    var question: String = ""
)