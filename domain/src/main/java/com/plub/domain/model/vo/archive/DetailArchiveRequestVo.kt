package com.plub.domain.model.vo.archive

import com.plub.domain.model.DomainModel

data class DetailArchiveRequestVo(
    val plubbingId : Int = -1,
    val archiveId : Int = -1,
) : DomainModel
