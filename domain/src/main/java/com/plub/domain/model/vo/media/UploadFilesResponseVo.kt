package com.plub.domain.model.vo.media

import com.plub.domain.model.DomainModel

data class UploadFilesResponseVo(
    val files:List<UploadFileResponseVo> = emptyList(),
): DomainModel