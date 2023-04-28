package com.plub.domain.model.vo.home.categoriesGatheringVo

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.DaysType
import com.plub.domain.model.vo.common.SelectedHobbyVo

data class FilterVo(
    val gatheringDays: HashSet<DaysType> = hashSetOf(),
    val accountNum : Int = 0,
    val isAll : Boolean = false,
    val selectedHobbies:List<SelectedHobbyVo> = emptyList()
) : DomainModel
