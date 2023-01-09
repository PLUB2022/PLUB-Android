package com.plub.presentation.ui.home.plubing

import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<PageState.Default>(PageState.Default) {


//    testPostHomeUseCase.invoke(HomePostRequestVo("testcode", false)).collect { state ->
//        when(state){
//            is UiState.Loading -> Log.d("테스트용", "로딩")
//            is UiState.Success -> Log.d("테스트용", "${state.successOrNull()!!.authCode}")
//            is UiState.Error -> Log.d("테스트용", "실패")
//        }
//    }
}
