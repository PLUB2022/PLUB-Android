package com.plub.presentation.ui.main.report.complete

import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportCompleteViewModel @Inject constructor(
) : BaseViewModel<ReportCompleteState>(ReportCompleteState()) {

    fun updateNowText(type : String){
        updateUiState { uiState ->
            uiState.copy(
                reportText = type
            )
        }
    }

    fun onClickBack(){
        emitEventFlow(ReportCompleteEvent.GoBack)
    }

    fun onClickHome(){
        emitEventFlow(ReportCompleteEvent.GoHome)
    }
}
