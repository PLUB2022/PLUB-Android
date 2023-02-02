package com.plub.presentation.ui.main.home.recruitment.apply


import android.widget.EditText
import androidx.core.view.get
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.plub.domain.model.enums.QuestionDataType
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitAnswerListVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitResponseVo
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.usecase.ApplicantsRecruitUseCase
import com.plub.domain.usecase.GetRecruitQuestionUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.ApplyEvent
import com.plub.presentation.ui.main.home.recruitment.apply.adapter.QuestionsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplyPlubbingViewModel @Inject constructor(
    val getRecruitQuestionUseCase: GetRecruitQuestionUseCase,
    val applicantsRecruitUseCase: ApplicantsRecruitUseCase
) : BaseViewModel<ApplyPageState>(ApplyPageState()) {

    fun fetchQuestions(plubbingId: Int) {
        viewModelScope.launch {
            getRecruitQuestionUseCase(plubbingId).collect { state ->
                inspectUiState(state, ::successFetchQuestions)
            }
        }
    }

    private fun successFetchQuestions(data: QuestionsResponseVo) {
        val questionsData = getDataList(data)
        updateUiState { ui ->
            ui.copy(
                questions = questionsData
            )
        }
    }

    private fun getDataList(data: QuestionsResponseVo): List<QuestionsDataVo> {
        val dataList: MutableList<QuestionsDataVo> = mutableListOf()
        dataList.add(0, QuestionsDataVo(viewType = QuestionDataType.FIRST))
        for (i in data.questions) {
            dataList.add(i)
        }
        return dataList
    }

    fun applyRecruit(plubbingId: Int, list: List<ApplicantsRecruitAnswerListVo>) {
        viewModelScope.launch {
            applicantsRecruitUseCase(
                ApplicantsRecruitRequestVo(
                    plubbingId,
                    list
                )
            ).collect { state ->
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

    fun isEmpty(questionsAdapter: QuestionsAdapter, recyclerview: RecyclerView) {
        var empty = false
        for (i in 1 until questionsAdapter.itemCount) {
            if (recyclerview.get(i).findViewById<EditText>(R.id.edit_text_answer).text.isEmpty()) {
                empty = true
            }
        }

        if (isDiffBtnState(!empty)) {
            updateButtonState(!empty)
        }
    }

    private fun isDiffBtnState(nowState: Boolean): Boolean {
        return nowState != uiState.value.isApplyButtonEnable
    }

    fun backPage() {
        emitEventFlow(ApplyEvent.BackPage)
    }

    fun getAnswerList(
        questionsAdapter: QuestionsAdapter,
        recyclerview: RecyclerView
    ): List<ApplicantsRecruitAnswerListVo> {
        val list: MutableList<ApplicantsRecruitAnswerListVo> = mutableListOf()
        for (i in 1 until questionsAdapter.itemCount) {
            list.add(
                ApplicantsRecruitAnswerListVo(
                    questionsAdapter.currentList[i].id,
                    recyclerview.get(i)
                        .findViewById<EditText>(R.id.edit_text_answer).text.toString()
                )
            )
        }
        return list
    }
}