package com.plub.presentation.ui.main.gathering.modify

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.enums.OnOfflineType
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailResponseVo
import com.plub.domain.usecase.GetRecruitDetailUseCase
import com.plub.domain.usecase.GetRecruitQuestionUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.gathering.create.question.CreateGatheringQuestion
import com.plub.presentation.ui.main.gathering.modify.guestQuestion.ModifyGuestQuestionPageState
import com.plub.presentation.ui.main.gathering.modify.info.ModifyInfoPageState
import com.plub.presentation.ui.main.gathering.modify.recruit.ModifyRecruitPageState
import com.plub.presentation.util.TimeFormatter
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

    private fun handleGetGatheringInfoSuccess(plubbingId: Int, data: RecruitDetailResponseVo) {
        updateUiState { uiState ->
            uiState.copy(
                modifyRecruitPageState = getRecruitPageState(plubbingId, data),
                modifyInfoPageState = getInfoPageState(plubbingId, data)
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

    private fun getInfoPageState(
        plubbingId: Int,
        data: RecruitDetailResponseVo
    ): ModifyInfoPageState {
        return ModifyInfoPageState(
            plubbingId = plubbingId,
            gatheringDays = data.plubbingDays.map { DaysType.findByEng(it) }.toHashSet(),
            gatheringOnOffline = if(data.plubbingDays.isEmpty()) OnOfflineType.ON.value else OnOfflineType.ON.value,
            address = data.address,
            roadAdress = data.roadAdress,
            placeName = data.placeName,
            gatheringHour = TimeFormatter.getIntHour(data.plubbingTime),
            gatheringMin = TimeFormatter.getIntMin(data.plubbingTime),
            gatheringFormattedTime = data.plubbingTime,
            seekBarProgress = data.curAccountNum,
            seekBarPositionX = 0.0f
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

    fun goToBack() {
        emitEventFlow(ModifyGatheringEvent.GoToBack)
    }

    fun goToModifyQuestion() {
        emitEventFlow(ModifyGatheringEvent.GoToModifyQuestion(uiState.value.modifyGuestQuestionPageState))
    }

    fun goToModifyRecruit() {
        emitEventFlow(ModifyGatheringEvent.GoToModifyRecruit(uiState.value.modifyRecruitPageState))
    }

    fun goToModifyInfo() {
        emitEventFlow(ModifyGatheringEvent.GoToModifyInfo(uiState.value.modifyInfoPageState))
    }
}