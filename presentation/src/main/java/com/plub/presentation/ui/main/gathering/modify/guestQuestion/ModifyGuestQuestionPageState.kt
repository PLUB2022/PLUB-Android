package com.plub.presentation.ui.main.gathering.modify.guestQuestion

import android.os.Parcelable
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.gathering.create.question.CreateGatheringQuestion
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModifyGuestQuestionPageState(
    val plubbingId: Int = -1,
    private val _questions: List<CreateGatheringQuestion> = listOf(CreateGatheringQuestion()),
    val isNeedQuestionCheck: Boolean? = null,
    val needUpdateRecyclerView: Boolean = true,
    val isAddQuestionButtonVisible: Boolean = false
) : PageState, Parcelable {
    val questions
        get() = if(isNeedQuestionCheck == true) _questions else emptyList()

    @IgnoredOnParcel
    val isSaveButtonEnabled =
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