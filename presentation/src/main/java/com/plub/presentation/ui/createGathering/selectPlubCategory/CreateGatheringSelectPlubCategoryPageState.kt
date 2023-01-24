package com.plub.presentation.ui.createGathering.selectPlubCategory

import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.presentation.state.PageState

data class CreateGatheringSelectPlubCategoryPageState(
    val categoriesVo: List<HobbyVo> = emptyList(),
    val categoriesSelectedVo: SignUpHobbiesVo = SignUpHobbiesVo()
) : PageState {
    val isNextButtonEnabled = categoriesSelectedVo.hobbies.isNotEmpty()
}