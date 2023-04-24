package com.plub.domain.model.vo.myPage

import com.plub.domain.model.DomainModel

data class MyPageMyProfileVo(
    val myName : String = "",
    val myIntro : String = "",
    val profileImage : String = ""
) : DomainModel
