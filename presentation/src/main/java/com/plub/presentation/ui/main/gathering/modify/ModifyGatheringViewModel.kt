package com.plub.presentation.ui.main.gathering.modify

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.enums.OnOfflineType
import com.plub.domain.error.GatheringError
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailResponseVo
import com.plub.domain.usecase.GetRecruitDetailUseCase
import com.plub.domain.usecase.GetRecruitQuestionUseCase
import com.plub.domain.usecase.PutPullUpGatheringUseCase
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
    private val getRecruitQuestionUseCase: GetRecruitQuestionUseCase,
    private val putPullUpGatheringUseCase: PutPullUpGatheringUseCase
) : BaseViewModel<ModifyGatheringPageState>(ModifyGatheringPageState()) {

    companion object {
        const val GATHERING_PEOPLE_MIN_VALUE = 4
    }

    private var plubbingId: Int = -1

    fun getGatheringInfoDetail(plubbingId: Int, onSuccess: () -> Unit) {
        this.plubbingId = plubbingId

        viewModelScope.launch {
            getRecruitDetailUseCase(plubbingId).collect { state ->
                inspectUiState(
                    state,
                    succeedCallback = {
                        handleGetGatheringInfoSuccess(it)
                        onSuccess()
                    },
                    individualErrorCallback = {_, individual ->
                        handleGatheringError(individual as GatheringError)
                    }
                )
            }
        }
    }

    private fun handleGatheringError(gatheringError: GatheringError){
        when(gatheringError){
            is GatheringError.NotFoundPlubbing -> TODO()
            else -> TODO()
        }
    }

    private fun handleGetGatheringInfoSuccess(data: RecruitDetailResponseVo) {
        updateUiState { uiState ->
            uiState.copy(
                modifyRecruitPageState = getRecruitPageState(data),
                modifyInfoPageState = getInfoPageState(data)
            )
        }
    }

    private fun getRecruitPageState(
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
        data: RecruitDetailResponseVo
    ): ModifyInfoPageState {
        return ModifyInfoPageState(
            plubbingId = plubbingId,
            gatheringDays = data.plubbingDays.map { DaysType.findByKor(it) }.toHashSet(),
            gatheringOnOffline = if(data.placeName.isBlank()) OnOfflineType.ON.value else OnOfflineType.OFF.value,
            address = data.address,
            roadAdress = data.roadAdress,
            placeName = data.placeName,
            gatheringHour = TimeFormatter.getIntHour(data.plubbingTime),
            gatheringMin = TimeFormatter.getIntMin(data.plubbingTime),
            gatheringFormattedTime = TimeFormatter.getAmPmHourMin(data.plubbingTime),
            seekBarProgress = data.remainAccountNum + data.curAccountNum - GATHERING_PEOPLE_MIN_VALUE,
            seekBarPositionX = 0.0f
        )
    }

    fun getQuestions() {
        viewModelScope.launch {
            getRecruitQuestionUseCase(plubbingId).collect { state ->
                inspectUiState(
                    state,
                    succeedCallback = { handleGetQuestionSuccess(it) },
                    individualErrorCallback = {_, individual ->
                        handleGatheringError(individual as GatheringError)
                    }
                )
            }
        }
    }

    private fun handleGetQuestionSuccess(data: QuestionsResponseVo) {
        updateUiState { uiState ->
            uiState.copy(
                modifyGuestQuestionPageState = getGuestQuestionPageState(data)
            )
        }
    }

    private fun getGuestQuestionPageState(
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

    fun pullUp() {
        viewModelScope.launch {
            putPullUpGatheringUseCase(plubbingId).collect {
                inspectUiState(it, { emitEventFlow(ModifyGatheringEvent.ShowPullUpSuccessToastMsg) })
            }
        }
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