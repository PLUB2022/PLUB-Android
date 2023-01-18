package com.plub.presentation.ui.home.plubing.recruitment


import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailRequestVo
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.domain.successOrNull
import com.plub.domain.usecase.RecruitDetailUseCase
import com.plub.presentation.state.SampleHomeState
import com.plub.domain.usecase.TestPostHomeUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruitmentViewModel @Inject constructor(
    val recruitDetailUseCase: RecruitDetailUseCase
) : BaseViewModel<PageState.Default>(PageState.Default) {

    private val _recruitMentDetailData = MutableSharedFlow<RecruitDetailResponseVo>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val recruitMentDetailData: SharedFlow<RecruitDetailResponseVo> = _recruitMentDetailData.asSharedFlow()


    fun fetchRecruitmentDetail(plubbingId : Int){
        viewModelScope.launch {
            recruitDetailUseCase.invoke(RecruitDetailRequestVo(plubbingId)).collect{ state ->
                state.successOrNull()?.let { _recruitMentDetailData.emit(it) }
            }
        }
    }

}