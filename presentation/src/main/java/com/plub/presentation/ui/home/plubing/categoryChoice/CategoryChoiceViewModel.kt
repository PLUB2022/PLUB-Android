package com.plub.presentation.ui.home.plubing.categoryChoice


import android.util.Log
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
class CategoryChoiceViewModel @Inject constructor(
    val testPostHomeUseCase: TestPostHomeUseCase
) : BaseViewModel<SampleHomeState>(SampleHomeState()) {

    private val _testHomeData = MutableStateFlow("")
    val testHomeData: StateFlow<String> = _testHomeData.asStateFlow()

    private val _switchList = MutableStateFlow("")
    val switchList: StateFlow<String> = _switchList.asStateFlow()

    private val _goToDetailRecruitmentFragment = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val goToDetailRecruitmentFragment: SharedFlow<Unit> = _goToDetailRecruitmentFragment.asSharedFlow()

    fun isHaveInterest()  = viewModelScope.launch {
        testPostHomeUseCase.invoke(HomePostRequestVo("123123", true)).collect { state ->
            _testHomeData.value = when(state){
                is UiState.Loading -> "로딩"
                is UiState.Success -> "${state.successOrNull()!!.authCode.toString()}"
                is UiState.Error -> "에러"
            }

        }
    }

    fun gridBtnClick(){
        //TODO 그리드로 변경
        Log.d("TAG", "그리드 변경 버튼")
        _switchList.value = "그리드"
    }

    fun listBtnClick(){
        //TODO 리스트로 변경
        Log.d("TAG", "리스트 변경 버튼")
        _switchList.value = "리스트"
    }

    fun goToCategoryChoice() {
        viewModelScope.launch {
            _goToDetailRecruitmentFragment.emit(Unit)
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