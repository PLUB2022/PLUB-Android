package com.plub.presentation.ui.main.home.recruitment.apply


import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.ApplyModifyApplicationType
import com.plub.domain.model.enums.ApplyRecruitQuestionViewType
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitAnswerVo
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.model.vo.home.recruitDetailVo.host.AnswersVo
import com.plub.domain.model.vo.myPage.MyPageMyApplicationVo
import com.plub.domain.usecase.GetMyApplicationUseCase
import com.plub.domain.usecase.PostApplyRecruitUseCase
import com.plub.domain.usecase.GetRecruitQuestionUseCase
import com.plub.domain.usecase.PutModifyMyApplicationUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplyPlubbingViewModel @Inject constructor(
    private val getRecruitQuestionUseCase: GetRecruitQuestionUseCase,
    private val postApplyRecruitUseCase: PostApplyRecruitUseCase,
    private val getMyApplicationUseCase: GetMyApplicationUseCase,
    private val putModifyMyApplicationUseCase: PutModifyMyApplicationUseCase
) : BaseViewModel<ApplyPageState>(ApplyPageState()) {

    companion object{
        const val EMPTY_TEXT = ""
        const val FIRST_INDEX = 0
    }
    private var answerList : List<ApplicantsRecruitAnswerVo> = emptyList()
    private var plubbingId : Int = 0

    fun fetchQuestions(id: Int, pageType : ApplyModifyApplicationType) {
        plubbingId = id
        updateUiState { uiState ->
            uiState.copy(
                pageType = pageType
            )
        }
        when(pageType){
            ApplyModifyApplicationType.APPLY -> getOnlyQuestion()
            ApplyModifyApplicationType.MODIFY -> getQuestionWithMyAnswer()
        }
    }

    private fun getOnlyQuestion(){
        viewModelScope.launch {
            getRecruitQuestionUseCase(plubbingId).collect { state ->
                inspectUiState(state, ::successFetchQuestions)
            }
        }
    }

    private fun getQuestionWithMyAnswer(){
        viewModelScope.launch {
            getMyApplicationUseCase(plubbingId).collect{
                inspectUiState(it, ::successFetchMyApplication)
            }
        }
    }

    private fun successFetchMyApplication(vo : MyPageMyApplicationVo){
        setAnswerListWithEditText(vo.answerList)
        val questionsData = getAnswerMergeList(vo.answerList)
        updateUiState { ui ->
            ui.copy(
                questions = questionsData
            )
        }
    }

    private fun setAnswerListWithEditText(list : List<AnswersVo>){
        val mergeList = mutableListOf<ApplicantsRecruitAnswerVo>()
        list.forEach {
            mergeList.add(ApplicantsRecruitAnswerVo(it.id, it.answer))
        }
        answerList = mergeList
    }

    private fun getAnswerMergeList(data: List<AnswersVo>): List<QuestionsDataVo> {
        val mergedList: MutableList<QuestionsDataVo> = mutableListOf()
        mergedList.add(FIRST_INDEX, QuestionsDataVo(viewType = ApplyRecruitQuestionViewType.FIRST))
        data.forEach {
            mergedList.add(
                QuestionsDataVo(
                    id = it.id,
                    question = it.questions,
                    answer = it.answer
                )
            )
        }
        return mergedList
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
        val mergeList = mutableListOf<ApplicantsRecruitAnswerVo>()
        dataList.forEach {
            mergeList.add(ApplicantsRecruitAnswerVo(it.id, EMPTY_TEXT))
        }
        answerList = mergeList
    }

    private fun getMergeList(data: QuestionsResponseVo): List<QuestionsDataVo> {
        val mergedList: MutableList<QuestionsDataVo> = mutableListOf()
        mergedList.add(0, QuestionsDataVo(viewType = ApplyRecruitQuestionViewType.FIRST))
        mergedList.addAll(data.questions)
        return mergedList
    }

    fun applyRecruit() {
        when(uiState.value.pageType){
            ApplyModifyApplicationType.APPLY -> applyButton()
            ApplyModifyApplicationType.MODIFY -> modifyButton()
        }
    }

    private fun applyButton(){
        viewModelScope.launch {
            postApplyRecruitUseCase(ApplicantsRecruitRequestVo(plubbingId, answerList)).collect { state ->
                inspectUiState(state, { successApply() } )
            }
        }
    }

    private fun modifyButton(){
        viewModelScope.launch {
            putModifyMyApplicationUseCase(ApplicantsRecruitRequestVo(plubbingId, answerList)).collect{
                inspectUiState(it, { backPage() })
            }
        }
    }

    private fun successApply() {
        emitEventFlow(ApplyEvent.ShowSuccessDialog)
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
        isAnswerListNotEmpty()
    }

    private fun isAnswerListNotEmpty() {
        var emptyState = true
        answerList.forEach {
            if(it.answer.isEmpty()){
                emptyState = false
            }
        }

        if (isDiffBtnState(emptyState)) {
            updateButtonState(emptyState)
        }
    }

    private fun isDiffBtnState(nowState: Boolean): Boolean {
        return nowState != uiState.value.isApplyButtonEnable
    }

    private fun updateButtonState(isAnswerListNotEmpty: Boolean) {
        updateUiState { ui ->
            ui.copy(
                isApplyButtonEnable = isAnswerListNotEmpty
            )
        }
    }

    fun backPage() {
        emitEventFlow(ApplyEvent.BackPage)
    }
}