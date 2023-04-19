package com.plub.presentation.ui.main.report

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.ReportBackgroundType
import com.plub.domain.model.vo.report.ReportItemVo
import com.plub.domain.model.vo.report.ReportVo
import com.plub.domain.usecase.GetReportUseCase
import com.plub.presentation.base.BaseTestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getReportUseCase: GetReportUseCase
) : BaseTestViewModel<ReportState>() {

    private val reportListStateFlow : MutableStateFlow<List<ReportItemVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState : ReportState = ReportState(
        reportList = reportListStateFlow.asStateFlow()
    )

    fun getReportList(){
        viewModelScope.launch {
            getReportUseCase(Unit).collect{
                inspectUiState(it, ::handleSuccessGetReport)
            }
        }
    }

    private fun handleSuccessGetReport(state : ReportVo){
        val mergedReportList = state.reportList.map {
            it.copy(
                reportBackgroundType = ReportBackgroundType.BUTTON
            )
        }
        viewModelScope.launch {
            reportListStateFlow.update { mergedReportList }
        }
    }

    fun goToReportDetailPage(){
        emitEventFlow(ReportEvent.GoToReport(0))
    }
}
