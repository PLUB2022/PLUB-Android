package com.plub.data.mapper.archiveMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.archive.ArchiveContentResponse
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo

object ArchiveContentResponseMapper : Mapper.ResponseMapper<ArchiveContentResponse, ArchiveContentResponseVo> {
    override fun mapDtoToModel(type: ArchiveContentResponse?): ArchiveContentResponseVo {
        return type?.run {
            ArchiveContentResponseVo(
                archiveId = archiveId,
                title = title,
                images = images,
                imageCount = imageCount,
                sequence = sequence,
                createdAt = createdAt,
                accessType = accessType
            )
        }?:ArchiveContentResponseVo()
    }
}