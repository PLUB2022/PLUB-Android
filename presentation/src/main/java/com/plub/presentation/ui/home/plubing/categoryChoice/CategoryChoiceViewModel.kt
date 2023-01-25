package com.plub.presentation.ui.home.plubing.categoryChoice


import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.categoriesgatheringresponse.CategoriesGatheringRequestVo
import com.plub.domain.model.vo.home.recommendationgatheringvo.RecommendationGatheringResponseVo
import com.plub.domain.successOrNull
import com.plub.domain.usecase.GetCategoriesGatheringUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryChoiceViewModel @Inject constructor(
    val categoriesGatheringUseCase: GetCategoriesGatheringUseCase
) : BaseViewModel<PageState.Default>(PageState.Default) {


    private val _switchList = MutableStateFlow("")
    val switchList: StateFlow<String> = _switchList.asStateFlow()

    private val _recommendationData =
        MutableSharedFlow<RecommendationGatheringResponseVo>(0, 1, BufferOverflow.DROP_OLDEST)
    val recommendationData: SharedFlow<RecommendationGatheringResponseVo> =
        _recommendationData.asSharedFlow()

    private val _goToDetailRecruitmentFragment = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val goToDetailRecruitmentFragment: SharedFlow<Unit> = _goToDetailRecruitmentFragment.asSharedFlow()

    fun fetchRecommendationGatheringData(categoryId : Int)  =
        viewModelScope.launch {
            categoriesGatheringUseCase.invoke(CategoriesGatheringRequestVo(categoryId, 0)).collect{ state->
                state.successOrNull()?.let { _recommendationData.emit(it) }
            }
        }

    fun gridBtnClick(){
        //TODO 그리드로 변경
        _switchList.value = "그리드"
    }

    fun listBtnClick(){
        //TODO 리스트로 변경
        _switchList.value = "리스트"
    }

    fun goToCategoryChoice() {
        viewModelScope.launch {
            _goToDetailRecruitmentFragment.emit(Unit)
        }
    }

}