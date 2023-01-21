package com.plub.presentation.ui.home.search

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.search.InsertRecentSearchVo
import com.plub.domain.model.vo.home.search.RecentSearchVo
import com.plub.domain.usecase.DeleteAllRecentSearchUseCase
import com.plub.domain.usecase.DeleteRecentSearchUseCase
import com.plub.domain.usecase.FetchRecentSearchUseCase
import com.plub.domain.usecase.InsertRecentSearchUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.event.SearchingEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchingViewModel @Inject constructor(
    val fetchRecentSearchUseCase: FetchRecentSearchUseCase,
    val deleteRecentSearchUseCase: DeleteRecentSearchUseCase,
    val deleteAllRecentSearchUseCase: DeleteAllRecentSearchUseCase,
    val insertRecentSearchUseCase: InsertRecentSearchUseCase,
) : BaseViewModel<SearchingPageState>(SearchingPageState()) {

    private var currentSize:Int? = null

    fun onDeleteRecentSearch(id:Int) {
        viewModelScope.launch {
            deleteRecentSearchUseCase(id).collect {
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

    fun onSearch(text:String) {
        currentSize?.let {
            viewModelScope.launch {
                val searchVo = RecentSearchVo(search = text, saveTime = System.currentTimeMillis())
                val request = InsertRecentSearchVo(it,searchVo)
                insertRecentSearchUseCase(request).collect {
                    inspectUiState(it, {
                        emitEventFlow(SearchingEvent.GoToSearchResult(text))
                    })
                }
            }
        }
    }

    private fun updateSearchList(list:List<RecentSearchVo>) {
        currentSize = list.size
        updateUiState { uiState ->
            uiState.copy(
                recentSearchList = list,
                isEmptyViewVisible = list.isEmpty()
            )
        }
    }
}
