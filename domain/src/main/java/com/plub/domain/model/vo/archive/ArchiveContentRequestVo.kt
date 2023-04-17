package com.plub.domain.model.vo.archive

import com.plub.domain.model.DomainModel

data class ArchiveContentRequestVo(
    val title : String = "",
    val images: List<String> = emptyList()
) : DomainModel
