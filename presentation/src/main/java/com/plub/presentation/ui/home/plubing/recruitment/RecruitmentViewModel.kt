package com.plub.presentation.ui.home.plubing.recruitment


import androidx.lifecycle.viewModelScope
import com.plub.presentation.state.SampleHomeState
import com.plub.domain.usecase.TestPostHomeUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruitmentViewModel @Inject constructor(
    val testPostHomeUseCase: TestPostHomeUseCase
) : BaseViewModel<SampleHomeState>(SampleHomeState()) {

    private val _goToApplyFragment = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val goToApplyFragment: SharedFlow<Unit> = _goToApplyFragment.asSharedFlow()

    fun goToCategoryChoice() {
        viewModelScope.launch {
            _goToApplyFragment.emit(Unit)
        }
    }


//    fun isHaveInterest()  = viewModelScope.launch {
//        testPostHomeUseCase.invoke(HomePostRequestVo("testcode", false)).collect { state ->
//            when(state){
//                is UiState.Loading -> updateUiState { uiState ->
//                    uiState.copy("로딩")
//                }
//                is UiState.Success -> updateUiState { uiState ->
//                    uiState.copy("성공 + ${state.data.toString()}")
//                }
//                is UiState.Error -> updateUiState { uiState ->
//                    uiState.copy("실패 + ${state.error.toString()}")
//                }
//            }
//        }

}