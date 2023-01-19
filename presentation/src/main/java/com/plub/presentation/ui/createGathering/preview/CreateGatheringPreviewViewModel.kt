package com.plub.presentation.ui.createGathering.preview

import androidx.lifecycle.viewModelScope
import com.plub.domain.usecase.FetchMyInfoUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGatheringPreviewViewModel @Inject constructor(
    private val fetchMyInfoUseCase: FetchMyInfoUseCase
) :
    BaseViewModel<CreateGatheringPreviewPageState>(CreateGatheringPreviewPageState()) {

    init {
        viewModelScope.launch {
            fetchMyInfoUseCase(Unit)
        }
    }
}