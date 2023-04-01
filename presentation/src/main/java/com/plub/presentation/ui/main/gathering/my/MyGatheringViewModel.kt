package com.plub.presentation.ui.main.gathering.my

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.MyGatheringsViewType
import com.plub.domain.model.vo.myGathering.MyGatheringResponseVo
import com.plub.domain.model.vo.myGathering.MyGatheringsResponseVo
import com.plub.domain.usecase.GetMyHostingGatheringsUseCase
import com.plub.domain.usecase.GetMyParticipatingGatheringsUseCase
import com.plub.presentation.base.BaseTestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyGatheringViewModel @Inject constructor(
    private val getMyParticipatingGatheringsUseCase: GetMyParticipatingGatheringsUseCase,
    private val getMyHostingGatheringsUseCase: GetMyHostingGatheringsUseCase
) : BaseTestViewModel<MyGatheringPageState>() {

    private val _myGatherings: MutableStateFlow<List<MyGatheringResponseVo>> = MutableStateFlow(
        emptyList()
    )
    private val _myHostings: MutableStateFlow<List<MyGatheringResponseVo>> = MutableStateFlow(
        emptyList()
    )
    override val uiState: MyGatheringPageState = MyGatheringPageState(
        myGatherings = _myGatherings.asStateFlow(),
        myHostings = _myHostings.asStateFlow()
    )

    fun onClickRadioButtonMyGathering(isChecked: Boolean) {
        if(isChecked) getMyParticipatingGatherings()
    }

    fun getMyParticipatingGatherings() = viewModelScope.launch {
        if(uiState.myGatherings.value.isNotEmpty()) return@launch

        clearMyHostings()

        getMyParticipatingGatheringsUseCase(Unit).collect { uiState ->
            inspectUiState(uiState, succeedCallback = { data ->
                updateMyGatherings(
                    data.plubbings.plus(
                        MyGatheringResponseVo(viewType = MyGatheringsViewType.PARTICIPATE)
                    )
                )
            })
        }
    }

    private fun getMyHostingGatherings() = viewModelScope.launch {
        if(uiState.myHostings.value.isNotEmpty()) return@launch

        clearMyGatherings()

        getMyHostingGatheringsUseCase(Unit).collect { uiState ->
            inspectUiState(uiState, succeedCallback = { data ->
                updateMyHostings(
                    data.plubbings.plus(
                        MyGatheringResponseVo(viewType = MyGatheringsViewType.CREATE)
                    )
                )
            })
        }
    }

    fun onClickRadioButtonHost(isChecked: Boolean) {
        if(isChecked) getMyHostingGatherings()
    }

    private fun clearMyGatherings() {
        _myGatherings.update {
            emptyList()
        }
    }

    private fun clearMyHostings() {
        _myHostings.update {
            emptyList()
        }
    }

    private fun updateMyGatherings(items: List<MyGatheringResponseVo>) {
        _myGatherings.update {
            items
        }
    }

    private fun updateMyHostings(items: List<MyGatheringResponseVo>) {
        _myHostings.update {
            items
        }
    }

    fun goToPlubingMain(plubbingId: Int) {
        emitEventFlow(
            MyGatheringEvent.GoToPlubingMain(plubbingId)
        )
    }
}