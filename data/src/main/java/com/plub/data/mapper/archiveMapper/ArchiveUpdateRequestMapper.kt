package com.plub.data.mapper.archiveMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.archive.ArchiveUpdateRequest
import com.plub.domain.model.vo.archive.ArchiveContentRequestVo

object ArchiveUpdateRequestMapper : Mapper.RequestMapper<ArchiveUpdateRequest, ArchiveContentRequestVo> {
    override fun mapModelToDto(type: ArchiveContentRequestVo): ArchiveUpdateRequest {
        return type?.run {
            ArchiveUpdateRequest(
                title = title,
                images = images
            )
        }?:ArchiveUpdateRequest()
    }
}