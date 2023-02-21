package com.plub.presentation.ui.main.home.categoryChoice.filter

import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.domain.model.vo.signUp.hobbies.SignUpHobbiesVo
import com.plub.presentation.ui.PageState

data class GatheringFilterState(
    val categoryName : String ="",
    val subHobbies : List<SubHobbyVo> = emptyList(),
    val hobbiesSelectedVo: SignUpHobbiesVo = SignUpHobbiesVo()
): PageState
