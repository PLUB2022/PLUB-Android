package com.plub.domain.model.state.createGathering

import com.plub.domain.model.state.PageState

data class CreateGatheringQuestionPageState(
    val questions: List<CreateGatheringQuestion> = listOf(CreateGatheringQuestion()),
    val needUpdateRecyclerView: Boolean = true,
    val isAddQuestionButtonVisible: Boolean = false
) : PageState

data class CreateGatheringQuestion(
    var key: Int = 0,
    var position: Int = 1,
    var question: String = ""
)