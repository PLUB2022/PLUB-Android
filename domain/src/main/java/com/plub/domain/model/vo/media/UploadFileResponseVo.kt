package com.plub.domain.model.vo.media

import com.plub.domain.base.DomainModel

data class UploadFileResponseVo(
    val filename:String = "",
    val fileUrl:String = ""
): DomainModel