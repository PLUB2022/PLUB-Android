package com.plub.domain.model.vo.media

import com.plub.domain.base.DomainModel
import com.plub.domain.model.enums.UploadFileType
import java.io.File

data class UploadFileRequestVo(
    val type: UploadFileType,
    val file: File,
): DomainModel()