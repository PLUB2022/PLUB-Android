package com.plub.domain.model.vo.archive

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.ArchiveItemViewType

data class ArchiveUploadVo(
    val viewType :ArchiveItemViewType = ArchiveItemViewType.EDIT_VIEW,
    val editText : String = "",
    val image : String = ""
) : DomainModel
