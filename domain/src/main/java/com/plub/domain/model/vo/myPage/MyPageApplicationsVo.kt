package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel
import com.plub.domain.model.vo.home.recruitDetailVo.host.AnswersVo

data class MyPageApplicationsVo(
    val profileImage : String ="",
    val name : String = "",
    val date : String = "",
    val answerList : List<AnswersVo> = emptyList()
) : DomainModel
