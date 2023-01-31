package com.plub.presentation.ui.main.gathering.createGathering

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.CreateGatheringPageType
import com.plub.domain.model.enums.CreateGatheringPageType.SELECT_PLUB_CATEGORY
import com.plub.domain.model.enums.CreateGatheringPageType.GATHERING_TITLE_AND_NAME
import com.plub.domain.model.enums.CreateGatheringPageType.GOAL_INTRODUCE_PICTURE
import com.plub.domain.model.enums.CreateGatheringPageType.DAY_ON_OFF_LOCATION
import com.plub.domain.model.enums.CreateGatheringPageType.PEOPLE_NUMBER
import com.plub.domain.model.enums.CreateGatheringPageType.QUESTION
import com.plub.domain.model.enums.CreateGatheringPageType.PREVIEW
import com.plub.domain.model.enums.CreateGatheringPageType.FINISH
import com.plub.domain.model.enums.OnOfflineType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.createGathering.CreateGatheringRequestVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.usecase.PostCreateGatheringUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.presentation.ui.main.gathering.createGathering.goalAndIntroduceAndImage.CreateGatheringGoalAndIntroduceAndPicturePageState
import com.plub.presentation.ui.main.gathering.createGathering.gatheringTitleAndName.CreateGatheringTitleAndNamePageState
import com.plub.presentation.ui.main.gathering.createGathering.dayAndOnOfflineAndLocation.CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState
import com.plub.presentation.ui.main.gathering.createGathering.peopleNumber.CreateGatheringPeopleNumberPageState
import com.plub.presentation.ui.main.gathering.createGathering.question.CreateGatheringQuestionPageState
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.gathering.createGathering.selectPlubCategory.CreateGatheringSelectPlubCategoryPageState
import com.plub.presentation.ui.main.gathering.createGathering.finish.CreateGatheringFinishPageState
import com.plub.presentation.ui.main.gathering.createGathering.preview.CreateGatheringPreviewPageState
import com.plub.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGatheringViewModel @Inject constructor(
    val postUploadFileUseCase: PostUploadFileUseCase,
    val postCreateGatheringUseCase: PostCreateGatheringUseCase
) :
    BaseViewModel<CreateGatheringPageState>(CreateGatheringPageState()) {

    private var currentPage = 0
    private val maxPage = CreateGatheringPageType.values().size - 1

    private val childrenPageStateMap: MutableMap<Int, PageState> = mutableMapOf(
        SELECT_PLUB_CATEGORY.idx to CreateGatheringSelectPlubCategoryPageState(),
        GATHERING_TITLE_AND_NAME.idx to CreateGatheringTitleAndNamePageState(),
        GOAL_INTRODUCE_PICTURE.idx to CreateGatheringGoalAndIntroduceAndPicturePageState(),
        DAY_ON_OFF_LOCATION.idx to CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState(),
        PEOPLE_NUMBER.idx to CreateGatheringPeopleNumberPageState(),
        QUESTION.idx to CreateGatheringQuestionPageState(),
        PREVIEW.idx to CreateGatheringPreviewPageState(),
        FINISH.idx to CreateGatheringFinishPageState()
    )

    private val _childrenPageStateFlow = MutableStateFlow<PageState>(PageState.Default)
    val childrenPageStateFlow: SharedFlow<PageState> = _childrenPageStateFlow.asStateFlow()

    fun onClickNextButtonInPreViewPage() {
        uploadPlubbingMainImage {
            createGathering(it) {
                goToNextPageAndEmitChildrenPageState()
            }
        }
    }

    private fun createGathering(mainImageUrl: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val request = getCreateGatheringRequestVo(mainImageUrl)

            postCreateGatheringUseCase(request).collect { state ->
                inspectUiState(state, { onSuccess() })
            }
        }
    }

    private fun getCreateGatheringRequestVo(mainImageUrl: String): CreateGatheringRequestVo {
        with(uiState.value) {
            val gatheringLocationData =
                if (dayAndOnOfflineAndLocationPageState.gatheringOnOffline == OnOfflineType.OFF.value)
                    dayAndOnOfflineAndLocationPageState.gatheringLocationData
                else null

            return CreateGatheringRequestVo(
                subCategoryIds = selectPlubCategoryPageState.categoriesSelectedVo.hobbies.map { it.subId },
                title = titleAndNamePageState.introductionTitle,
                name = titleAndNamePageState.gatheringName,
                goal = goalAndIntroduceAndPicturePageState.gatheringGoal,
                introduce = goalAndIntroduceAndPicturePageState.gatheringIntroduce,
                mainImage = mainImageUrl,
                time = TimeFormatter.getHHmm(
                    dayAndOnOfflineAndLocationPageState.gatheringHour,
                    dayAndOnOfflineAndLocationPageState.gatheringMin
                ),
                days = dayAndOnOfflineAndLocationPageState.gatheringDays.map { it.eng },
                onOff = dayAndOnOfflineAndLocationPageState.gatheringOnOffline,
                address = gatheringLocationData?.addressName,
                roadAddress = gatheringLocationData?.roadAddressName,
                placeName = gatheringLocationData?.placeName,
                placePositionX = gatheringLocationData?.placePositionX?.toFloat(),
                placePositionY = gatheringLocationData?.placePositionY?.toFloat(),
                maxAccountNum = peopleNumberPageState.peopleNumber,
                questions = questionPageState.questions.map { it.question }
            )
        }
    }

    fun updateChildrenPageState() {
        val selectPlubCategoryPageState =
            childrenPageStateMap[SELECT_PLUB_CATEGORY.idx] as? CreateGatheringSelectPlubCategoryPageState
                ?: CreateGatheringSelectPlubCategoryPageState()
        val titleAndNamePageState =
            childrenPageStateMap[GATHERING_TITLE_AND_NAME.idx] as? CreateGatheringTitleAndNamePageState
                ?: CreateGatheringTitleAndNamePageState()
        val goalAndIntroduceAndPicturePageState =
            childrenPageStateMap[GOAL_INTRODUCE_PICTURE.idx] as? CreateGatheringGoalAndIntroduceAndPicturePageState
                ?: CreateGatheringGoalAndIntroduceAndPicturePageState()
        val dayAndOnOfflineAndLocationPageState =
            childrenPageStateMap[DAY_ON_OFF_LOCATION.idx] as? CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState
                ?: CreateGatheringDayAndTimeAndOnOfflineAndLocationPageState()
        val peopleNumberPageState =
            childrenPageStateMap[PEOPLE_NUMBER.idx] as? CreateGatheringPeopleNumberPageState
                ?: CreateGatheringPeopleNumberPageState()
        val questionPageState =
            childrenPageStateMap[QUESTION.idx] as? CreateGatheringQuestionPageState
                ?: CreateGatheringQuestionPageState()

        updateUiState { ui ->
            ui.copy(
                selectPlubCategoryPageState = selectPlubCategoryPageState,
                titleAndNamePageState = titleAndNamePageState,
                goalAndIntroduceAndPicturePageState = goalAndIntroduceAndPicturePageState,
                dayAndOnOfflineAndLocationPageState = dayAndOnOfflineAndLocationPageState,
                peopleNumberPageState = peopleNumberPageState,
                questionPageState = questionPageState
            )
        }
    }

    private fun uploadPlubbingMainImage(onSuccess: (String) -> Unit) = viewModelScope.launch {
        val pageState = childrenPageStateMap[GOAL_INTRODUCE_PICTURE.idx]
        if (pageState is CreateGatheringGoalAndIntroduceAndPicturePageState) {
            pageState.gatheringImage?.let { file ->
                val fileRequest = UploadFileRequestVo(UploadFileType.PLUBBING_MAIN, file)

                postUploadFileUseCase(fileRequest).collect { state ->
                    inspectUiState(
                        state,
                        succeedCallback = {
                            onSuccess(it.fileUrl)
                        },
                        individualErrorCallback = { _, _ ->
                            //TODO ERROR 처리
                        }
                    )
                }
            } ?: onSuccess("")
        } else {
            //TODO 알 수 없는 에러 처리
        }
    }

    fun onMoveToNextPage(pageState: PageState) {
        if (isLastPage()) return
        setChildrenPageStateAndGoToNextPageAndEmitChildrenPageState(pageState)
    }

    private fun setChildrenPageStateAndGoToNextPageAndEmitChildrenPageState(pageState: PageState) {
        updateUiState { uiState ->
            setChildrenPageState(pageState)
            emitChildrenPageState(++currentPage)
            uiState.copy(currentPage = currentPage)
        }
    }

    private fun goToNextPageAndEmitChildrenPageState() {
        updateUiState { uiState ->
            emitChildrenPageState(++currentPage)
            uiState.copy(currentPage = currentPage)
        }
    }

    fun setChildrenPageState(pageState: PageState) {
        childrenPageStateMap[currentPage] = pageState
    }

    private fun emitChildrenPageState(idx: Int) {
        viewModelScope.launch {
            val childrenPageState = childrenPageStateMap[idx] ?: PageState.Default
            _childrenPageStateFlow.emit(childrenPageState)
        }
    }

    fun onBackPressed() {
        if(isFirstPage()) emitEventFlow(com.plub.presentation.ui.main.gathering.createGathering.CreateGatheringEvent.NavigationPopEvent)
        else emitEventFlow(com.plub.presentation.ui.main.gathering.createGathering.CreateGatheringEvent.GoToPrevPage)
    }

    fun goToPrevPageAndEmitChildrenPageState() {
        updateUiState { uiState ->
            emitChildrenPageState(--currentPage)
            uiState.copy(currentPage = currentPage)
        }
    }

    private fun isFirstPage() = currentPage == 0
    private fun isLastPage() = currentPage == maxPage
}