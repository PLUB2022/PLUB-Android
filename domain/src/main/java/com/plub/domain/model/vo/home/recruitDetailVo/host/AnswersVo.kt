package com.plub.domain.model.vo.home.recruitDetailVo.host

import com.plub.domain.model.DomainModel

data class AnswersVo(
    val id : Int = -1,
    val questions: String = "",
    val answer : String =""
):DomainModel