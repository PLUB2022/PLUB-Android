package com.plub.presentation.ui.home.plubing

import androidx.lifecycle.viewModelScope
import com.plub.domain.UiState
import com.plub.domain.model.vo.home.HomePostRequestVo
import com.plub.domain.successOrNull
import com.plub.domain.usecase.TestPostHomeUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.SampleHomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    val testPostHomeUseCase: TestPostHomeUseCase
) : BaseViewModel<SampleHomeState>(SampleHomeState()) {

    private val _testHomeData = MutableStateFlow("")
    val testHomeData: StateFlow<String> = _testHomeData.asStateFlow()

    private val _goToCategoryChoiceFragment = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val goToCategoryChoiceFragment: SharedFlow<Unit> = _goToCategoryChoiceFragment.asSharedFlow()

    fun isHaveInterest()  = viewModelScope.launch {
        testPostHomeUseCase.invoke(HomePostRequestVo("123123", true)).collect { state ->
            _testHomeData.value = when(state){
                is UiState.Loading -> "로딩"
                is UiState.Success -> "${state.successOrNull()!!.authCode.toString()}"
                is UiState.Error -> "에러"
            }

        }
    }

    fun goToCategoryChoice() {
        viewModelScope.launch {
            _goToCategoryChoiceFragment.emit(Unit)
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