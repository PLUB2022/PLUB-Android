package com.plub.presentation.ui.main.home.recruitment.apply


import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.ApplyRecruitQuestionViewType
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitAnswerListVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.usecase.PostApplyRecruitUseCase
import com.plub.domain.usecase.GetRecruitQuestionUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplyPlubbingViewModel @Inject constructor(
    val getRecruitQuestionUseCase: GetRecruitQuestionUseCase,
    val postApplyRecruitUseCase: PostApplyRecruitUseCase
) : BaseViewModel<ApplyPageState>(ApplyPageState()) {

    private var answerList : List<ApplicantsRecruitAnswerListVo> = emptyList()
    private var plubbingId : Int = 0

    fun fetchQuestions(id: Int) {
        plubbingId = id
        viewModelScope.launch {
            getRecruitQuestionUseCase(plubbingId).collect { state ->
                inspectUiState(state, ::successFetchQuestions)
            }
        }
    }

    private fun successFetchQuestions(data: QuestionsResponseVo) {
        setAnswerList(data.questions)
        val questionsData = getMergeList(data)
        updateUiState { ui ->
            ui.copy(
                questions = questionsData
            )
        }
    }

    private fun setAnswerList(dataList : List<QuestionsDataVo>){
        val mergeList = mutableListOf<ApplicantsRecruitAnswerListVo>()
        for(content in dataList){
            mergeList.add(ApplicantsRecruitAnswerListVo(content.id, ""))
        }
        answerList = mergeList
    }

    private fun getMergeList(data: QuestionsResponseVo): List<QuestionsDataVo> {
        val mergedList: MutableList<QuestionsDataVo> = mutableListOf()
        mergedList.add(0, QuestionsDataVo(viewType = ApplyRecruitQuestionViewType.FIRST))
        for (i in data.questions) {
            mergedList.add(i)
        }
        return mergedList
    }

    fun applyRecruit() {
        viewModelScope.launch {
            postApplyRecruitUseCase(ApplicantsRecruitRequestVo(plubbingId, answerList)).collect { state ->
                inspectUiState(state, ::successApply)
            }
        }
    }

    private fun successApply(data: ApplicantsRecruitResponseVo) {
        emitEventFlow(ApplyEvent.ShowSuccessDialog)
    }

    private fun updateButtonState(flag: Boolean) {
        updateUiState { ui ->
            ui.copy(
                isApplyButtonEnable = flag
            )
        }
    }

    fun isEmpty() {
        var emptyState = false
        for (content in answerList) {
            if (content.answer.isEmpty()) {
                emptyState = true
            }
        }

        if (isDiffBtnState(!emptyState)) {
            updateButtonState(!emptyState)
        }
    }

    private fun isDiffBtnState(nowState: Boolean): Boolean {
        return nowState != uiState.value.isApplyButtonEnable
    }

    fun textChanged(questionId : Int, text : String){
        val list = answerList
        val newList = list.map {
            val changedText = if (it.questionId == questionId) text else it.answer
            it.copy(
                answer = changedText
            )
        }
        answerList = newList
        isEmpty()
    }

    fun backPage() {
        emitEventFlow(ApplyEvent.BackPage)
    }
}