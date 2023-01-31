package com.plub.presentation.ui.main.home.search

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.usecase.DeleteAllRecentSearchUseCase
import com.plub.domain.usecase.DeleteRecentSearchUseCase
import com.plub.domain.usecase.FetchRecentSearchUseCase
import com.plub.domain.usecase.InsertRecentSearchUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.SearchingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchingViewModel @Inject constructor(
    val fetchRecentSearchUseCase: FetchRecentSearchUseCase,
    val deleteRecentSearchUseCase: DeleteRecentSearchUseCase,
    val deleteAllRecentSearchUseCase: DeleteAllRecentSearchUseCase,
) : BaseViewModel<SearchingPageState>(SearchingPageState()) {

    fun onDeleteRecentSearch(search: String) {
        viewModelScope.launch {
            deleteRecentSearchUseCase(search).collect {
                inspectUiState(it, {
                    fetchRecentSearchList()
                })
            }
        }
    }

    fun fetchRecentSearchList() {
        viewModelScope.launch {
            fetchRecentSearchUseCase(Unit).collect {
                inspectUiState(it, { list ->
                    updateSearchList(list)
                })
            }
        }
    }

    fun onDeleteAllRecentSearch() {
        viewModelScope.launch {
            deleteAllRecentSearchUseCase(Unit).collect {
                inspectUiState(it, {
                    updateSearchList(emptyList())
                })
            }
        }
    }

    fun onSearch(text: String) {
        goToSearchResult(text)
    }

    fun onRecentSearch(text: String) {
        goToSearchResult(text)
    }

    private fun goToSearchResult(search: String) {
        emitEventFlow(SearchingEvent.Clear)
        emitEventFlow(SearchingEvent.HideKeyboard)
        emitEventFlow(SearchingEvent.GoToSearchResult(search))
    }

    private fun updateSearchList(list: List<RecentSearchVo>) {
        updateUiState { uiState ->
            uiState.copy(
                recentSearchList = list,
                isEmptyViewVisible = list.isEmpty()
            )
        }
    }
}
