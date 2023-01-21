package com.plub.domain.model.vo.home.recruitdetailvo.host

import com.plub.domain.base.DomainModel

data class AccountsVo (
    val accountName : String,
    val profileImage : String,
    val createdAt : String,
    val answers : List<AnswersVo>
        ):DomainModel()