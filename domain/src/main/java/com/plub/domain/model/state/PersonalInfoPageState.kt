package com.plub.domain.model.state

import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo

data class PersonalInfoPageState(
    val isNextButtonEnable:Boolean = false,
    val personalInfoVo: PersonalInfoVo = PersonalInfoVo(),
    val yearList:List<Int> = emptyList(),
    val monthList:List<Int> = emptyList(),
    val dayList:List<Int> = emptyList(),
    val defaultYear:Int = 0,
    val defaultMonth:Int = 0,
    val defaultDay:Int = 0,
): PageState