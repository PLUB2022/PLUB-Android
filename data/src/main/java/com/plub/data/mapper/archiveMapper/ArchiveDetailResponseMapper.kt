package com.plub.data.mapper.archiveMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.archive.ArchiveDetailResponse
import com.plub.domain.model.vo.archive.ArchiveDetailResponseVo

object ArchiveDetailResponseMapper : Mapper.ResponseMapper<ArchiveDetailResponse, ArchiveDetailResponseVo> {
    override fun mapDtoToModel(type: ArchiveDetailResponse?): ArchiveDetailResponseVo {
        return type?.run {
            ArchiveDetailResponseVo(
                images = images,
                sequence = sequence,
                createdAt = createdAt
            )
        }?: ArchiveDetailResponseVo()
    }
}