package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.plub.domain.model.state.PageState
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.usecase.FetchKakaoLocationByKeywordUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.switchMap
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetSearchLocationViewModel @Inject constructor(
    private val fetchKakaoLocationByKeywordUseCase: FetchKakaoLocationByKeywordUseCase
) :
    BaseViewModel<PageState.Default>(
        PageState.Default
    ) {

    val editTextValue = MutableStateFlow("")

    private val query = MutableStateFlow("")

    val upDateEditTextValue: (text: String) -> Unit = { text ->
        editTextValue.value = text
    }

    val locationData = query.mapLatest {  query ->
        fetchKakaoLocationByKeywordUseCase(query)
    }

    fun onClickKeyboardSearch(): Void? {
        viewModelScope.launch {
            query.value = editTextValue.value
        }
        return null
    }
}