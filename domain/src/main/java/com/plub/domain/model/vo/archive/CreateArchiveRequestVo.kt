package com.plub.domain.model.vo.archive

import com.plub.domain.model.DomainModel

data class CreateArchiveRequestVo(
    val plubbingId : Int = -1,
    val body : ArchiveContentRequestVo = ArchiveContentRequestVo()
):DomainModel
