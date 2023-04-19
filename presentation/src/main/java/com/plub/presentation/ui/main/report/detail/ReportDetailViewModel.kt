package com.plub.presentation.ui.main.report.detail

import com.plub.domain.model.enums.ReportBackgroundType
import com.plub.domain.model.vo.report.ReportItemVo
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportDetailViewModel @Inject constructor(
) : BaseViewModel<ReportDetailState>(ReportDetailState()) {

    private var isExpand : Boolean = false

    //TODO 서버에서 가져오기
//    val arrayList = arrayListOf(
//        ReportItemVo("비속어 / 폭언 / 비하 / 음란성 내용0번쨰", ReportBackgroundType.SPINNER,0),
//        ReportItemVo("비속어 / 폭언 / 비하 / 음란성 내용1번째", ReportBackgroundType.SPINNER,1),
//        ReportItemVo("비속어 / 폭언 / 비하 / 음란성 내용2번째", ReportBackgroundType.SPINNER,2),
//        ReportItemVo("비속어 / 폭언 / 비하 / 음란성 내용3번째", ReportBackgroundType.SPINNER,3)
//    )

    fun getReportList() {
//        val string = when(type){
//            0 -> "비속어 / 폭언 / 비하 / 음란성 내용0번쨰"
//            1 -> "비속어 / 폭언 / 비하 / 음란성 내용1번쨰"
//            2 -> "비속어 / 폭언 / 비하 / 음란성 내용2번쨰"
//            3 -> "비속어 / 폭언 / 비하 / 음란성 내용3번쨰"
//            else -> {""}
//        }
//        updateUiState { uiState ->
//            uiState.copy(
//                nowText = string,
//                reportList = arrayList.filter { type != it.reportType }
//            )
//        }
    }

    fun onClickSpinner(){
        if(isExpand) emitEventFlow(ReportDetailEvent.GoneSpinner)
        else emitEventFlow(ReportDetailEvent.ShowSpinner)
        isExpand = !isExpand
    }

    fun onTextChangedAfter(){
        if(uiState.value.reportContent.isNotEmpty()) {
            emitEventFlow(ReportDetailEvent.BorderBlack)
            updateButtonState(true)
        }
        else {
            emitEventFlow(ReportDetailEvent.BorderDefault)
            updateButtonState(false)
        }
    }

    fun goToComplete(){
        emitEventFlow(ReportDetailEvent.GoToComplete(uiState.value.nowText))
    }

    private fun updateButtonState(enable : Boolean){
        updateUiState { uiState->
            uiState.copy(
                isButtonEnable = enable
            )
        }
    }

}
