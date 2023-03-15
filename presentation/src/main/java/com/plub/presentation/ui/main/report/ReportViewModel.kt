package com.plub.presentation.ui.main.report

import com.plub.domain.model.vo.report.ReportItemVo
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
) : BaseViewModel<ReportState>(ReportState()) {

    fun getReportList(){
        //TODO 서버에서 가져오기
        updateUiState { uiState ->
            uiState.copy(
                reportList = arrayListOf(
                    ReportItemVo("비속어 / 폭언 / 비하 / 음란성 내용", 0),
                    ReportItemVo("비속어 / 폭언 / 비하 / 음란성 내용", 1),
                    ReportItemVo("비속어 / 폭언 / 비하 / 음란성 내용", 2),
                    ReportItemVo("비속어 / 폭언 / 비하 / 음란성 내용", 3)
                )
            )
        }
    }

    fun goToReportDetailPage(type : Int){
        emitEventFlow(ReportEvent.GoToReport(type))
    }
}
