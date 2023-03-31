package com.plub.presentation.ui.main.profile.bottomsheet

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.OtherProfileBottomSheetViewType
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.myPage.OtherProfileVo
import com.plub.domain.usecase.GetOtherProfileUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetProfileViewModel @Inject constructor(
    private val getOtherProfileUseCase: GetOtherProfileUseCase
) : BaseViewModel<BottomSheetOtherProfileState>(
    BottomSheetOtherProfileState()
) {

    fun fetchOtherProfile(nickName : String, accountId : Int){
        viewModelScope.launch {
            getOtherProfileUseCase(nickName).collect{
                inspectUiState(it, ::onSuccessGetOtherInfo)
            }
        }
    }

    private fun onSuccessGetOtherInfo(vo : MyInfoResponseVo){
        updateUiState { uiState ->
            uiState.copy(
                dataList = listOf(
                    OtherProfileVo(
                        info = vo,
                        viewType = OtherProfileBottomSheetViewType.PROFILE
                    )
                )
            )
        }
    }
}