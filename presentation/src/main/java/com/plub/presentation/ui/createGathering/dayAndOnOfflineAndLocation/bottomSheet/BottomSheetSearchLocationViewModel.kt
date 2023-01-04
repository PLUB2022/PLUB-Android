package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.plub.domain.model.state.CreateGatheringKakaoLocationBottomSheetPageState
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.usecase.FetchKakaoLocationByKeywordUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
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
    private val query = MutableStateFlow("")

    val upDateEditTextValue: (text: String) -> Unit = { text ->
        updateUiState { uiState ->
            uiState.copy(
                searchText = text
            )
        }
    }

    private fun upDateSearchResultCount(count: Int) {
        updateUiState { uiState ->
            uiState.copy(
                searchResultCount = count
            )
        }
    }

    val locationData = query.mapLatest { query ->
        fetchKakaoLocationByKeywordUseCase(query).map { pageData ->
            pageData.map {
                upDateSearchResultCount(it.documentTotalCount)
                it
            }
        }.cachedIn(viewModelScope)
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
            query.value = uiState.value.searchText
        }
        return null
    }
}