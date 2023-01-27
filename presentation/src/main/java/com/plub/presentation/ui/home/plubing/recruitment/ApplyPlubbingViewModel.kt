package com.plub.presentation.ui.home.plubing.recruitment


import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitAnswerListVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.successOrNull
import com.plub.domain.usecase.ApplicantsRecruitUseCase
import com.plub.domain.usecase.GetRecruitQuestionUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.ApplyPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplyPlubbingViewModel @Inject constructor(
    val getRecruitQuestionUseCase: GetRecruitQuestionUseCase,
    val applicantsRecruitUseCase: ApplicantsRecruitUseCase
) : BaseViewModel<ApplyPageState>(ApplyPageState()) {

    private val _recruitQuestionData = MutableSharedFlow<QuestionsResponseVo>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val recruitQuestionData: SharedFlow<QuestionsResponseVo> = _recruitQuestionData.asSharedFlow()

    fun fetchQuestions(plubbingId : Int){
        viewModelScope.launch {
            getRecruitQuestionUseCase.invoke(plubbingId).collect{ state ->
                inspectUiState(state, ::successFetchQuestions)
            }
        }
    }

    private fun successFetchQuestions(data : QuestionsResponseVo){
        updateUiState {ui->
            ui.copy(
                questionsData = data
            )
        }
        //_recruitQuestionData.emit(data)
    }

    fun applyRecruit(plubbingId: Int, list : List<ApplicantsRecruitAnswerListVo>){
        viewModelScope.launch {
            applicantsRecruitUseCase(ApplicantsRecruitRequestVo(plubbingId, list))
        }
    }

    fun updateButtonState(flag : Boolean){
        updateUiState { ui ->
            ui.copy(
                isApplyButtonEnable = flag
            )
        }
        uiState.value.isApplyButtonEnable

    }
}