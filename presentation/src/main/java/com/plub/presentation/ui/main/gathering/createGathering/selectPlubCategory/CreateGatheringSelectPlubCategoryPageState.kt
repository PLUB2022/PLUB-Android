package com.plub.presentation.ui.main.gathering.createGathering.selectPlubCategory

import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.presentation.ui.PageState

data class CreateGatheringSelectPlubCategoryPageState(
    val categoriesVo: List<HobbyVo> = emptyList(),
    val categoriesSelectedVo: SignUpHobbiesVo = SignUpHobbiesVo()
) : PageState {
    val isNextButtonEnabled = categoriesSelectedVo.hobbies.isNotEmpty()
}