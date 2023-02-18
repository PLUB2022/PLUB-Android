package com.plub.domain.model.vo.media

import com.plub.domain.model.DomainModel
import com.plub.domain.model.enums.UploadFileType
import java.io.File

data class ChangeFileRequestVo(
    val type: UploadFileType,
    val toDeleteUrls: String,
    val file: File,
): DomainModel