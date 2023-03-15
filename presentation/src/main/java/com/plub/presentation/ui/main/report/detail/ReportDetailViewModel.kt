package com.plub.presentation.ui.main.report.detail

import com.plub.domain.model.vo.report.ReportItemVo
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportDetailViewModel @Inject constructor(
) : BaseViewModel<ReportDetailState>(ReportDetailState()) {
    fun getReportList(type : Int) {

        val string = when(type){
            0 -> "비속어 / 폭언 / 비하 / 음란성 내용0번쨰"
            1 -> "비속어 / 폭언 / 비하 / 음란성 내용1번쨰"
            2 -> "비속어 / 폭언 / 비하 / 음란성 내용2번쨰"
            3 -> "비속어 / 폭언 / 비하 / 음란성 내용3번쨰"
            else -> {""}
        }
        updateNowReportType(string)
        //TODO 서버에서 가져오기
        val arrayList = arrayListOf(
            ReportItemVo("비속어 / 폭언 / 비하 / 음란성 내용0번쨰", 0),
            ReportItemVo("비속어 / 폭언 / 비하 / 음란성 내용1번째", 1),
            ReportItemVo("비속어 / 폭언 / 비하 / 음란성 내용2번째", 2),
            ReportItemVo("비속어 / 폭언 / 비하 / 음란성 내용3번째", 3)
        )
        updateUiState { uiState ->
            uiState.copy(
                reportList = arrayList.filter { uiState.nowText != it.reportTitle }
            )
        }
    }

    fun updateNowReportType(string : String){
        updateUiState { uiState ->
            uiState.copy(
                nowText = string
            )
        }
    }

}
