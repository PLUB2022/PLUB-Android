package com.plub.domain.model.vo.archive

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.ArchiveAccessType
import com.plub.domain.model.enums.ArchiveViewType

data class ArchiveContentResponseVo(
    val archiveId : Int = -1,
    val title : String = "",
    val images : List<String> = emptyList(),
    val imageCount : Int = -1,
    val sequence : Int = -1,
    val createdAt : String = "",
    val accessType : ArchiveAccessType = ArchiveAccessType.NORMAL,
    val viewType: ArchiveViewType = ArchiveViewType.ARCHIVE
) : DomainModel
