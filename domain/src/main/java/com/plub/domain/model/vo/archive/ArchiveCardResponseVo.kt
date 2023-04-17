package com.plub.domain.model.vo.archive

import com.plub.domain.model.DomainModel

data class ArchiveCardResponseVo(
    val last : Boolean = false,
    val content : List<ArchiveContentResponseVo> = emptyList()
) : DomainModel
