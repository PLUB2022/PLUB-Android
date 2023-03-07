package com.plub.presentation.ui.main.gathering.modifyGathering.guestQuestion

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.modifyGathering.ModifyQuestionRequestVo
import com.plub.domain.usecase.PutModifyQuestionsUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.gathering.createGathering.question.CreateGatheringQuestion
import com.plub.presentation.ui.main.gathering.createGathering.question.CreateGatheringQuestionViewModel
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.deepCopy
import com.plub.presentation.util.deepCopyAfterUpdateQuestion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyGuestQuestionViewModel @Inject constructor(
    private val putModifyQuestionsUseCase: PutModifyQuestionsUseCase
) : BaseViewModel<ModifyGuestQuestionPageState>(ModifyGuestQuestionPageState()) {

    fun initPageState(bundlePageState: ModifyGuestQuestionPageState) {
        updateUiState { uiState ->
            uiState.copy(
                plubbingId = bundlePageState.plubbingId,
                _questions = bundlePageState.copy(isNeedQuestionCheck = true).questions.deepCopy(),
                isNeedQuestionCheck = bundlePageState.isNeedQuestionCheck,
                needUpdateRecyclerView = true,
                isAddQuestionButtonVisible = bundlePageState.isAddQuestionButtonVisible
            )
        }
    }

    fun onClickSaveButton() {
        viewModelScope.launch {
            val request = ModifyQuestionRequestVo(uiState.value.plubbingId, uiState.value.questions.map { it.question })
            putModifyQuestionsUseCase(request).collect { state ->
                inspectUiState(state,
                    succeedCallback = { },
                    individualErrorCallback = null)
            }
        }
    }

    fun onClickNeedQuestionButton() {
        viewModelScope.launch {
            updateUiState { uiState ->
                uiState.copy(
                    isNeedQuestionCheck = true,
                    needUpdateRecyclerView = true
                )
            }
        }
    }

    fun onClickNoQuestionButton() {
        viewModelScope.launch {
            updateUiState { uiState ->
                uiState.copy(
                    isNeedQuestionCheck = false,
                    needUpdateRecyclerView = false
                )
            }
        }
    }

    fun onClickRecyclerViewDeleteButton(position: Int) {
        emitEventFlow(
            ModifyGuestQuestionEvent.ShowBottomSheetDeleteQuestion(
                uiState.value.questions.size,
                position
            )
        )
    }

    fun updateQuestion(data: CreateGatheringQuestion, text: String) {
        updateUiState { uiState ->
            val updatedQuestion = uiState.questions.deepCopyAfterUpdateQuestion(data.key, text)

            uiState.copy(
                _questions = updatedQuestion,
                needUpdateRecyclerView = false,
                isAddQuestionButtonVisible = updatedQuestion.isNotEmpty() && updatedQuestion.find { it.question.isBlank() } == null && updatedQuestion.size != CreateGatheringQuestionViewModel.MAX_QUESTION_SIZE
            )
        }
    }

    fun addQuestion() {
        updateUiState { uiState ->
            val key = uiState.questions.lastOrNull()?.key ?: 0
            val emptyQuestion = CreateGatheringQuestion(
                key = key + 1,
                position = uiState.questions.size + 1
            )
            uiState.copy(
                _questions = uiState.questions.plus(emptyQuestion),
                needUpdateRecyclerView = true,
                isAddQuestionButtonVisible = false
            )
        }
    }

    fun onClickBottomSheetDelete(size: Int, position: Int) {
        deleteQuestionOrMakeQuestionSizeToOne(position)
        if (size == 1)
            emitEventFlow(ModifyGuestQuestionEvent.PerformClickNoQuestionRadioButton)
    }

    private fun deleteQuestionOrMakeQuestionSizeToOne(position: Int) {
        val data = uiState.value.questions.find { it.position == position } ?: return

        updateUiState { uiState ->
            val deleteIndex = uiState.questions.indexOf(data)
            val temp = uiState.questions.minus(data).deepCopy()
                .ifEmpty { listOf(CreateGatheringQuestion()) }

            updateQuestionPosition(deleteIndex, temp)
            uiState.copy(
                _questions = temp,
                needUpdateRecyclerView = true,
                isAddQuestionButtonVisible = temp.find { it.question.isBlank() } == null
            )
        }
    }

    private fun updateQuestionPosition(changeIndex: Int, questions: List<CreateGatheringQuestion>) {
        var key = uiState.value.questions.lastOrNull()?.key ?: 0
        questions.forEachIndexed { index, gatheringQuestion ->
            if (index >= changeIndex) {
                gatheringQuestion.position = index + 1
                gatheringQuestion.key = ++key
                gatheringQuestion.question = questions[index].question
            }
        }
    }

}