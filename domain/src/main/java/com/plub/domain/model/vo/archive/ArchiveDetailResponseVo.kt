package com.plub.domain.model.vo.archive

import com.plub.domain.model.DomainModel

data class ArchiveDetailResponseVo(
    val images : List<String> = emptyList(),
    val sequence: Int = -1,
    val createdAt : String = "",
    val title : String = ""
) : DomainModel
