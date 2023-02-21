package com.plub.presentation.ui.main.gathering.modifyGathering

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.domain.usecase.GetRecruitDetailUseCase
import com.plub.domain.usecase.GetRecruitQuestionUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.gathering.createGathering.question.CreateGatheringQuestion
import com.plub.presentation.ui.main.gathering.modifyGathering.guestQuestion.ModifyGuestQuestionPageState
import com.plub.presentation.ui.main.gathering.modifyGathering.recruit.ModifyRecruitPageState
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.deepCopy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyGatheringViewModel @Inject constructor(
    private val getRecruitDetailUseCase: GetRecruitDetailUseCase,
    private val getRecruitQuestionUseCase: GetRecruitQuestionUseCase
) : BaseViewModel<ModifyGatheringPageState>(ModifyGatheringPageState()) {

    fun getGatheringInfoDetail(plubbingId: Int, onSuccess: (Int) -> Unit) {
        viewModelScope.launch {
            getRecruitDetailUseCase(plubbingId).collect { state ->
                inspectUiState(
                    state,
                    succeedCallback = {
                        handleGetGatheringInfoSuccess(plubbingId, it)
                        onSuccess(plubbingId)
                    },
                    individualErrorCallback = null
                )
            }
        }
    }

    fun handleUiState(uiState: ModifyGatheringPageState) {
        if (uiState.modifyGuestQuestionPageState != ModifyGuestQuestionPageState()) {
            emitEventFlow(ModifyGatheringEvent.InitViewPager)
        }
    }

    private fun handleGetGatheringInfoSuccess(plubbingId: Int, data: RecruitDetailResponseVo) {
        updateUiState { uiState ->
            uiState.copy(
                modifyRecruitPageState = getRecruitPageState(plubbingId, data)
            )
        }
    }

    private fun getRecruitPageState(
        plubbingId: Int,
        data: RecruitDetailResponseVo
    ): ModifyRecruitPageState {
        return ModifyRecruitPageState(
            plubbingId = plubbingId,
            title = data.recruitTitle,
            name = data.plubbingName,
            goal = data.plubbingGoal,
            introduce = data.recruitIntroduce,
            plubbingMainImgUrl = data.plubbingMainImage,
        )
    }

    fun getQuestions(plubbingId: Int) {
        viewModelScope.launch {
            getRecruitQuestionUseCase(plubbingId).collect { state ->
                inspectUiState(
                    state,
                    succeedCallback = { handleGetQuestionSuccess(plubbingId, it) },
                    individualErrorCallback = null
                )
            }
        }
    }

    private fun handleGetQuestionSuccess(plubbingId: Int, data: QuestionsResponseVo) {
        updateUiState { uiState ->
            uiState.copy(
                modifyGuestQuestionPageState = getGuestQuestionPageState(plubbingId, data)
            )
        }
    }

    private fun getGuestQuestionPageState(
        plubbingId: Int,
        data: QuestionsResponseVo
    ): ModifyGuestQuestionPageState {
        return ModifyGuestQuestionPageState(
            plubbingId = plubbingId,
            _questions = if(data.questions.isNotEmpty()) data.questions.mapIndexed { index, questionsDataVo ->
                CreateGatheringQuestion(
                    key = index,
                    position = index + 1,
                    question = questionsDataVo.question
                )
            } else listOf(CreateGatheringQuestion()),
            isNeedQuestionCheck = data.questions.isNotEmpty()
        )
    }
}