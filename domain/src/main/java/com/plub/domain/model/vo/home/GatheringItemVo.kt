package com.plub.domain.model.vo.home

import com.plub.domain.base.DomainModel

data class GatheringItemVo(
    val img_res : String,
    val title : String,
    val intro : String
) : DomainModel()