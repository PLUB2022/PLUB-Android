package com.plub.presentation.ui.main.gathering.create.preview

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.categoryResponseVo.CategoryDefaultImageRequestVo
import com.plub.domain.model.vo.home.categoryResponseVo.CategoryDefaultImageResponseVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.domain.usecase.GetCategoryDefaultImageUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGatheringPreviewViewModel @Inject constructor(
    private val getCategoryDefaultImageUseCase: GetCategoryDefaultImageUseCase
) : BaseViewModel<CreateGatheringPreviewPageState>(CreateGatheringPreviewPageState()) {

    companion object{
        const val FIRST_INDEX = 0
    }

    fun updateMyInfoUrl() {
        updateUiState { ui ->
            ui.copy(profileUrl = PlubUser.info.profileImage)
        }
    }

    fun updateDefaultImage(signHobbiesUpVo : SignUpHobbiesVo){
        val request = CategoryDefaultImageRequestVo(signHobbiesUpVo.hobbies[FIRST_INDEX].parentId, signHobbiesUpVo.hobbies[FIRST_INDEX].subId)
        viewModelScope.launch {
            getCategoryDefaultImageUseCase(request).collect{
                inspectUiState(it, ::handleSuccessGetDefaultImage)
            }
        }
    }

    private fun handleSuccessGetDefaultImage(vo : CategoryDefaultImageResponseVo){
        updateUiState { ui ->
            ui.copy(defaultImage = vo.image)
        }
    }
}