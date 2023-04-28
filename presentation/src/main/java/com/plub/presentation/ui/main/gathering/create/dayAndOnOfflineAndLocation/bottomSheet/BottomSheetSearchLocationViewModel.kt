package com.plub.presentation.ui.main.gathering.create.dayAndOnOfflineAndLocation.bottomSheet

import androidx.lifecycle.viewModelScope
import androidx.paging.ItemSnapshotList
import androidx.paging.cachedIn
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.usecase.FetchKakaoLocationByKeywordUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetSearchLocationViewModel @Inject constructor(
    private val fetchKakaoLocationByKeywordUseCase: FetchKakaoLocationByKeywordUseCase
) : BaseViewModel<CreateGatheringKakaoLocationBottomSheetPageState>(
    CreateGatheringKakaoLocationBottomSheetPageState()
) {

    private var selectedPosition:Int? = null
    private val searchQuery = MutableStateFlow("")
    val pagingData = searchQuery.flatMapLatest {
        fetchKakaoLocationByKeywordUseCase(it).cachedIn(viewModelScope)
    }

    fun onClickLocationRecyclerItem(selectedKakaoLocationDocument: KakaoLocationInfoDocumentVo, position:Int) {
        updateUiState { uiState ->
            uiState.copy(
                selectedLocation = selectedKakaoLocationDocument
            )
        }
        notifySelectedItemChange(position)
    }

    fun onClickKeyboardSearch(text:String) {
        viewModelScope.launch {
            searchQuery.emit(text)
        }
        updateUiState { uiState ->
            uiState.copy(
                searchedText = text,
                showSearchDescription = false,
                selectedLocation = null
            )
        }
        selectedPosition = null
        emitEventFlow(KakaoLocationEvent.HideKeyboard)
    }

    fun onClickConfirm() {
        val selectedVo = uiState.value.selectedLocation
        emitEventFlow(KakaoLocationEvent.ConfirmDialog(selectedVo))
    }

    fun onPageUpdated(snapshotList: ItemSnapshotList<KakaoLocationInfoDocumentVo>) {
        val totalCount = snapshotList.items.firstOrNull()?.documentTotalCount
        updatePageTotalCount(totalCount ?: 0)
    }

    private fun notifySelectedItemChange(position: Int) {
        emitEventFlow(KakaoLocationEvent.NotifyItemChanged(position))
        selectedPosition?.let {
            emitEventFlow(KakaoLocationEvent.NotifyItemChanged(it))
        }
        selectedPosition = position
    }

    private fun updatePageTotalCount(count: Int) {
        updateUiState { uiState ->
            uiState.copy(
                searchResultCount = count
            )
        }
    }
}