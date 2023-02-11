package com.plub.domain.model.vo.archive

import com.plub.domain.model.DomainModel

data class BrowseAllArchiveRequestVo(
    val plubbindId : Int = -1,
    val page : Int = -1
) : DomainModel
