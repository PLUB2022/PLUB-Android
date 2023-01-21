package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.usecase.FetchKakaoLocationByKeywordUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetSearchLocationViewModel @Inject constructor(
    private val fetchKakaoLocationByKeywordUseCase: FetchKakaoLocationByKeywordUseCase
) :
    BaseViewModel<CreateGatheringKakaoLocationBottomSheetPageState>(
        CreateGatheringKakaoLocationBottomSheetPageState()
    )
{
    private val _hideKeyboard = MutableSharedFlow<Unit>(0, 1, BufferOverflow.DROP_OLDEST)
    val hideKeyboard: SharedFlow<Unit> = _hideKeyboard.asSharedFlow()

    private val query = MutableStateFlow("")

    val upDateEditTextValue: (text: String) -> Unit = { text ->
        updateUiState { uiState ->
            uiState.copy(
                searchingText = text
            )
        }
    }

    private fun invisibleSearchDescription() {
        updateUiState { uiState ->
            uiState.copy(
                showSearchDescription = false
            )
        }
    }

    fun upDateSearchResultCount(count: Int) {
        updateUiState { uiState ->
            uiState.copy(
                searchResultCount = count
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val locationData = query.mapLatest { query ->
        fetchKakaoLocationByKeywordUseCase(query).cachedIn(viewModelScope)
    }

    fun onClickLocationRecyclerItem(selectedKakaoLocationDocument: KakaoLocationInfoDocumentVo) {
        updateUiState { uiState ->
            uiState.copy(
                selectedLocation = selectedKakaoLocationDocument
            )
        }
    }

    fun onClickKeyboardSearch(): Void? {
        viewModelScope.launch {
            query.value = uiState.value.searchingText
            _hideKeyboard.emit(Unit)
            invisibleSearchDescription()
        }
        return null
    }

    fun updateSearchedText() {
        viewModelScope.launch {
            updateUiState { ui ->
                ui.copy(
                    searchedText = query.value
                )
            }
        }
    }
}