package com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.bottomSheet

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.plub.domain.model.state.PageState
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.usecase.FetchKakaoLocationByKeywordUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BottomSheetSearchLocationViewModel @Inject constructor(
    private val fetchKakaoLocationByKeywordUseCase: FetchKakaoLocationByKeywordUseCase
) :
BaseViewModel<PageState.Default>(
    PageState.Default
)
{
    fun getLocation(query: String): Flow<PagingData<KakaoLocationInfoDocumentVo>> {
        return fetchKakaoLocationByKeywordUseCase(query)
    }
}