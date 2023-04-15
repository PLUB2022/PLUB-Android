package com.plub.domain.model.vo.media

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.UploadFileType

data class DeleteFileRequestVo(
    val type: UploadFileType,
    val file: String,
) : DomainModel
