package com.plub.presentation.ui.home.plubing.recruitment


import android.util.Log
import com.plub.domain.model.state.SampleHomeState
import com.plub.domain.usecase.TestPostHomeUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApplyPlubbingViewModel @Inject constructor(
    val testPostHomeUseCase: TestPostHomeUseCase
) : BaseViewModel<SampleHomeState>(SampleHomeState()) {


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