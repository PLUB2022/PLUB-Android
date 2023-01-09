package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.media.UploadFilesResponse
import com.plub.domain.model.vo.media.UploadFileResponseVo

object UploadFileResponseMapper : Mapper.ResponseMapper<UploadFilesResponse, UploadFileResponseVo> {
    override fun mapDtoToModel(type: UploadFilesResponse?): UploadFileResponseVo {
        return type?.run {
            files.firstOrNull()?.let {
                UploadFileResponseVo(it.filename, it.fileUrl)
            } ?: UploadFileResponseVo()
        } ?: UploadFileResponseVo()
    }
}