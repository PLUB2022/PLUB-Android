package com.plub.presentation.ui.main.report.detail

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.ReportReasonType
import com.plub.domain.model.enums.ReportTargetType
import com.plub.domain.model.sealed.ReportType
import com.plub.domain.model.vo.report.CreateReportRequestVo
import com.plub.domain.model.vo.report.ReportItemVo
import com.plub.domain.model.vo.report.ReportVo
import com.plub.domain.usecase.GetReportUseCase
import com.plub.domain.usecase.PostCreateReportUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportDetailViewModel @Inject constructor(
    private val getReportUseCase: GetReportUseCase,
    private val postCreateReportUseCase: PostCreateReportUseCase
) : BaseViewModel<ReportDetailState>(ReportDetailState()) {

    private var isExpand : Boolean = false
    private var reportList : List<ReportItemVo> = emptyList()
    private var reportTarget : String = ""
    private var reportTargetId : Int = 0
    private var reportPlubbingId : Int = 0
    private var reportType : String = ""

    fun getReportList(reasonType: ReportReasonType){
        viewModelScope.launch {
            getReportUseCase(Unit).collect{
                inspectUiState(it, {state -> handleSuccessGetReportList(state, reasonType)})
            }
        }
    }

    fun setReportTarget(report : ReportType){
        when(report){
            is ReportType.AccountReport -> {
                reportTarget = ReportTargetType.ACCOUNT.type
                setReportTargetIdAndPlubbingId(report.accountId, report.plubbingId)
            }

            is ReportType.ArchiveReport -> {
                reportTarget = ReportTargetType.ARCHIVE.type
                setReportTargetIdAndPlubbingId(report.archiveId, report.plubbingId)
            }

            is ReportType.FeedCommentReport -> {
                reportTarget = ReportTargetType.FEED_COMMENT.type
                setReportTargetIdAndPlubbingId(report.commentId, report.plubbingId)
            }

            is ReportType.FeedReport -> {
                reportTarget = ReportTargetType.FEED.type
                setReportTargetIdAndPlubbingId(report.feedId, report.plubbingId)
            }

            is ReportType.NoticeCommentReport -> {
                reportTarget = ReportTargetType.NOTICE_COMMENT.type
                setReportTargetIdAndPlubbingId(report.commentId, report.plubbingId)
            }

            is ReportType.RecruitReport -> {
                reportTarget = ReportTargetType.RECRUIT.type
                setReportTargetIdAndPlubbingId(report.plubbingId, report.plubbingId)
            }

            is ReportType.TodoReport -> {
                reportTarget = ReportTargetType.TODO.type
                setReportTargetIdAndPlubbingId(report.todoId, report.plubbingId)
            }
        }
    }

    private fun setReportTargetIdAndPlubbingId(targetId : Int, plubbingId : Int){
        reportTargetId = targetId
        reportPlubbingId = plubbingId
    }

    private fun handleSuccessGetReportList(state : ReportVo, reasonType : ReportReasonType){
        reportList = state.reportList
        setNowReport(reasonType)
    }

    fun setNowReport(reasonType: ReportReasonType) {
        val nowReport = reportList.find {
            it.reportType == reasonType
        }
        reportType = nowReport?.reportType?.type ?: ""
        updateUiState { uiState ->
            uiState.copy(
                nowText = nowReport?.reportTitle ?: "",
                reportList = reportList.filter { reasonType != it.reportType }
            )
        }
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

    fun onClickCreateReport(){
        val request = CreateReportRequestVo(
            reportType = reportType,
            reportTarget = reportTarget,
            reportTargetId = reportTargetId,
            reportReason = uiState.value.reportContent,
            plubbingId = reportPlubbingId
        )
        viewModelScope.launch {
            postCreateReportUseCase(request).collect{
                inspectUiState(it, {handleSuccessPostCreateReport()})
            }
        }
    }

    private fun handleSuccessPostCreateReport(){
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
