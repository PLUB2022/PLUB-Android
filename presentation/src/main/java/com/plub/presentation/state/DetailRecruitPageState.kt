package com.plub.presentation.state

import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo

data class DetailRecruitPageState(
    val canApply : Boolean = true,
    val recruitDetailData : RecruitDetailResponseVo =RecruitDetailResponseVo()
) : PageState