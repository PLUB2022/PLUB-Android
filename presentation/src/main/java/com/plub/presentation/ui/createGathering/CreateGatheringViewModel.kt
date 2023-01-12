package com.plub.presentation.ui.createGathering

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
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.presentation.ui.createGathering.goalAndIntroduceAndImage.CreateGatheringGoalAndIntroduceAndPicturePageState
import com.plub.presentation.ui.createGathering.gatheringTitleAndName.CreateGatheringTitleAndNamePageState
import com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.CreateGatheringDayAndOnOfflineAndLocationPageState
import com.plub.presentation.ui.createGathering.peopleNumber.CreateGatheringPeopleNumberPageState
import com.plub.presentation.ui.createGathering.question.CreateGatheringQuestionPageState
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.PageState
import com.plub.presentation.ui.createGathering.selectPlubCategory.CreateGatheringSelectPlubCategoryPageState
import com.plub.presentation.ui.createGathering.finish.CreateGatheringFinishPageState
import com.plub.presentation.ui.createGathering.preview.CreateGatheringPreviewPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGatheringViewModel @Inject constructor(
    val postUploadFileUseCase: PostUploadFileUseCase
) :
    BaseViewModel<CreateGatheringPageState>(CreateGatheringPageState()) {

    private var currentPage = 0
    private val maxPage = CreateGatheringPageType.values().size - 1

    private val childrenPageStateMap: MutableMap<Int, PageState> = mutableMapOf(
        SELECT_PLUB_CATEGORY.idx to CreateGatheringSelectPlubCategoryPageState(),
        GATHERING_TITLE_AND_NAME.idx to CreateGatheringTitleAndNamePageState(),
        GOAL_INTRODUCE_PICTURE.idx to CreateGatheringGoalAndIntroduceAndPicturePageState(),
        DAY_ON_OFF_LOCATION.idx to CreateGatheringDayAndOnOfflineAndLocationPageState(),
        PEOPLE_NUMBER.idx to CreateGatheringPeopleNumberPageState(),
        QUESTION.idx to CreateGatheringQuestionPageState(),
        PREVIEW.idx to CreateGatheringPreviewPageState(),
        FINISH.idx to CreateGatheringFinishPageState()
    )

    private val _childrenPageStateFlow = MutableStateFlow<PageState>(PageState.Default)
    val childrenPageStateFlow: SharedFlow<PageState> = _childrenPageStateFlow.asStateFlow()

    fun onMoveToNextPage(pageState: PageState) {
        if (isLastPage()) return
        setChildrenPageState(pageState)
        updateUiState { uiState ->
            uiState.copy(currentPage = ++currentPage)
        }
        emitChildrenPageState(currentPage)
    }

    private fun setChildrenPageState(pageState: PageState) {
        childrenPageStateMap[currentPage] = pageState
    }

    private fun emitChildrenPageState(idx: Int) {
        viewModelScope.launch {
            val childrenPageState = childrenPageStateMap[idx] ?: PageState.Default
            _childrenPageStateFlow.emit(childrenPageState)
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

    fun isFirstPage() = currentPage == 0
    fun isLastPage() = currentPage == maxPage
}