package com.plub.domain.model.vo.archive

import com.plub.domain.model.DomainModel

data class ArchiveContentResponseVo(
    val archiveId : Int = -1,
    val title : String = "",
    val images : List<String> = emptyList(),
    val imageCount : Int = -1,
    val sequence : Int = -1,
    val createdAt : String = ""
) : DomainModel
