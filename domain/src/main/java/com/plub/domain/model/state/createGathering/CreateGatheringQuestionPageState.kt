package com.plub.domain.model.state.createGathering

import com.plub.domain.model.state.PageState

data class CreateGatheringQuestionPageState(
    private val _questions: List<CreateGatheringQuestion> = listOf(CreateGatheringQuestion()),
    val isNeedQuestionCheck: Boolean? = null,
    val needUpdateRecyclerView: Boolean = true,
    val isAddQuestionButtonVisible: Boolean = false
) : PageState {
    val questions
        get() = if(isNeedQuestionCheck == true) _questions else emptyList()
    val isNextButtonEnabled =
        if(isNeedQuestionCheck == true) {
            _questions.find { it.question.isBlank() } == null
        } else {
            true
        }
}

data class CreateGatheringQuestion(
    var key: Int = 0,
    var position: Int = 1,
    var question: String = ""
)