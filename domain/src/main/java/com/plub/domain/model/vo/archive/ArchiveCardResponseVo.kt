package com.plub.domain.model.vo.archive

import com.plub.domain.model.DomainModel

data class ArchiveCardResponseVo(
    val totalPages : Int = -1,
    val content : List<ArchiveContentResponseVo> = emptyList()
) : DomainModel
