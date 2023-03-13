package com.plub.data.mapper.archiveMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.archive.ArchiveResponse
import com.plub.domain.model.vo.archive.ArchiveCardResponseVo

object ArchivesResponseMapper : Mapper.ResponseMapper<ArchiveResponse, ArchiveCardResponseVo> {
    override fun mapDtoToModel(type: ArchiveResponse?): ArchiveCardResponseVo {
        return type?.run{
            ArchiveCardResponseVo(
                content = content.map {
                    ArchiveContentResponseMapper.mapDtoToModel(it)
                }
            )
        }?: ArchiveCardResponseVo()
    }
}