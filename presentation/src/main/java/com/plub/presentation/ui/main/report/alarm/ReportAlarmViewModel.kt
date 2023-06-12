package com.plub.presentation.ui.main.report.alarm

import androidx.lifecycle.viewModelScope
import com.plub.domain.error.ReportError
import com.plub.domain.model.vo.report.ReportDetailVo
import com.plub.domain.usecase.GetReportDetailUseCase
import com.plub.presentation.base.BaseTestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportAlarmViewModel @Inject constructor(
    private val getReportDetailUseCase: GetReportDetailUseCase
) : BaseTestViewModel<ReportAlarmState>() {

    companion object{
        const val INDEX_DATE = 0
        const val SPLIT_OF_DATE_DASH = "-"
        const val SPLIT_OF_DATE_DOT = "."
    }

    private val titleStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val dateStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val contentStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState : ReportAlarmState = ReportAlarmState(
        title = titleStateFlow.asStateFlow(),
        date = dateStateFlow.asStateFlow(),
        content = contentStateFlow.asStateFlow()
    )

    fun getReportDetail(reportId : Int){
        viewModelScope.launch {
            getReportDetailUseCase(reportId).collect{
                inspectUiState(it, ::handleSuccessGetReportDetail, individualErrorCallback = {_, individual ->
                    handleReportError(individual as ReportError)
                })
            }
        }
    }

    private fun handleReportError(reportError: ReportError){
        when(reportError){
            is ReportError.AlreadyRevokeSuspendAccount -> TODO()
            ReportError.Common -> TODO()
            is ReportError.DuplicatedReport -> TODO()
            is ReportError.NotFoundReport -> TODO()
            is ReportError.NotFoundReportTarget -> TODO()
            is ReportError.NotFoundSuspendAccount -> TODO()
        }
    }

    private fun handleSuccessGetReportDetail(vo : ReportDetailVo){
        val date = vo.reportedAt.split(" ")
        viewModelScope.launch{
            titleStateFlow.update { vo.reportTitle }
            dateStateFlow.update { date[INDEX_DATE].replace(SPLIT_OF_DATE_DASH, SPLIT_OF_DATE_DOT)}
            contentStateFlow.update { vo.reportContent }
        }
    }
}
