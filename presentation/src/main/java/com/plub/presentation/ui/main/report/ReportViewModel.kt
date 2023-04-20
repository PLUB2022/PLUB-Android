package com.plub.presentation.ui.main.report

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.ReportBackgroundType
import com.plub.domain.model.enums.ReportReasonType
import com.plub.domain.model.sealed.ReportType
import com.plub.domain.model.vo.report.ReportItemVo
import com.plub.domain.model.vo.report.ReportVo
import com.plub.domain.usecase.GetReportUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getReportUseCase: GetReportUseCase,
    private val resourceProvider: ResourceProvider
) : BaseTestViewModel<ReportState>() {

    private val reportListStateFlow : MutableStateFlow<List<ReportItemVo>> = MutableStateFlow(
        emptyList()
    )
    private val reportTitleStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState : ReportState = ReportState(
        reportList = reportListStateFlow.asStateFlow(),
        reportTitle = reportTitleStateFlow.asStateFlow()
    )

    fun setTitle(type : ReportType){
        val resId = when(type){
            is ReportType.AccountReport -> R.string.report_account_title
            is ReportType.ArchiveReport -> R.string.report_archive_title
            is ReportType.FeedCommentReport -> R.string.report_comment_title
            is ReportType.FeedReport -> R.string.report_feed_title
            is ReportType.NoticeCommentReport -> R.string.report_comment_title
            is ReportType.RecruitReport -> R.string.report_recruit_title
            is ReportType.TodoReport -> R.string.report_todo_title
        }
        updateTitle(resId)
    }

    private fun updateTitle(resId : Int){
        viewModelScope.launch {
            reportTitleStateFlow.update { resourceProvider.getString(resId) }
        }
    }

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

    fun goToReportDetailPage(reasonType : ReportReasonType){
        emitEventFlow(ReportEvent.GoToReport(reasonType))
    }

    fun goToBack(){
        emitEventFlow(ReportEvent.GoToBack)
    }
}
