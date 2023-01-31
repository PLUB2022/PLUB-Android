package com.plub.presentation.ui.home.plubing.recruitment


import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitAnswerListVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.successOrNull
import com.plub.domain.usecase.ApplicantsRecruitUseCase
import com.plub.domain.usecase.GetRecruitQuestionUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.ApplyEvent
import com.plub.presentation.state.ApplyPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplyPlubbingViewModel @Inject constructor(
    val getRecruitQuestionUseCase: GetRecruitQuestionUseCase,
    val applicantsRecruitUseCase: ApplicantsRecruitUseCase
) : BaseViewModel<ApplyPageState>(ApplyPageState()) {

    fun fetchQuestions(plubbingId : Int){
        viewModelScope.launch {
            getRecruitQuestionUseCase(plubbingId).collect{ state ->
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
    }

    fun applyRecruit(plubbingId: Int, list : List<ApplicantsRecruitAnswerListVo>){
        viewModelScope.launch {
            applicantsRecruitUseCase(ApplicantsRecruitRequestVo(plubbingId, list)).collect{state->
                inspectUiState(state, ::successApply)
            }
        }
    }

    fun successApply(data : ApplicantsRecruitResponseVo){
        emitEventFlow(ApplyEvent.ShowSuccessDialog)
    }

    fun updateButtonState(flag : Boolean){
        updateUiState { ui ->
            ui.copy(
                isApplyButtonEnable = flag
            )
        }
    }

    fun backPage(){
        emitEventFlow(ApplyEvent.BackPage)
    }
}