package com.plub.presentation.ui.main.report.complete

import androidx.lifecycle.viewModelScope
import com.plub.presentation.base.BaseTestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportCompleteViewModel @Inject constructor(
) : BaseTestViewModel<ReportCompleteState>() {

    private val reportTextStateFlow : MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: ReportCompleteState = ReportCompleteState(
        reportText = reportTextStateFlow.asStateFlow()
    )

    fun updateNowText(type : String){
        viewModelScope.launch {
            reportTextStateFlow.update { type }
        }
    }

    fun onClickComplete(){
        emitEventFlow(ReportCompleteEvent.GoBack)
    }
}
