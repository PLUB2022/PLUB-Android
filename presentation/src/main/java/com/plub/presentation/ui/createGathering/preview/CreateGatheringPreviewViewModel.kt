package com.plub.presentation.ui.createGathering.preview

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.usecase.FetchMyInfoUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGatheringPreviewViewModel @Inject constructor(
    private val fetchMyInfoUseCase: FetchMyInfoUseCase
) :
    BaseViewModel<CreateGatheringPreviewPageState>(CreateGatheringPreviewPageState()) {

    fun fetchMyInfoUrl() {
        viewModelScope.launch {
            fetchMyInfoUseCase(Unit).collect { state ->
                inspectUiState(state, ::handleFetchMyInfoSuccess)
            }
        }
    }

    private fun handleFetchMyInfoSuccess(response: MyInfoResponseVo) {
        updateMyProfileUrl(response.profileImage)
    }

    private fun updateMyProfileUrl(url: String?) {
        updateUiState { ui ->
            ui.copy(profileUrl = url)
        }
    }
}