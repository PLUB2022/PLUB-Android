package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.media.DeleteFileResponse
import com.plub.data.dto.media.UploadFilesResponse
import com.plub.domain.model.vo.media.UploadFileResponseVo

object DeleteImageFileResponseMapper : Mapper.ResponseMapper<DeleteFileResponse, String> {
    override fun mapDtoToModel(type: DeleteFileResponse?): String {
        return type.toString() ?: ""
    }
}