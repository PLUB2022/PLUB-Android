package com.plub.domain.model.vo.modifyGathering

import com.plub.domain.model.DomainModel

data class ModifyRecruitRequestVo(
    val title:String,
    val name: String,
    val goal: String,
    val introduce: String,
    val mainImage: String,
): DomainModel