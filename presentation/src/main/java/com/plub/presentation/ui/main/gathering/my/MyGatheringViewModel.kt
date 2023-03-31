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
    override val uiState: MyGatheringPageState = MyGatheringPageState(
        myGatherings = _myGatherings.asStateFlow()
    )

    fun onClickRadioButtonMyGathering(isChecked: Boolean) {
        if(isChecked) getMyParticipatingGatherings()
    }

    fun getMyParticipatingGatherings() = viewModelScope.launch {
        clearMyGatherings()

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
        clearMyGatherings()

        getMyHostingGatheringsUseCase(Unit).collect { uiState ->
            inspectUiState(uiState, succeedCallback = { data ->
                updateMyGatherings(
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

    private fun updateMyGatherings(items: List<MyGatheringResponseVo>) {
        _myGatherings.update {
            items
        }
    }
}