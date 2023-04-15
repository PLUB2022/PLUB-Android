package com.plub.data.mapper.archiveMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.archive.ArchiveUpdateResponse
import com.plub.domain.model.vo.archive.ArchiveIdResponseVo

object ArchiveUpdateResponseMapper :Mapper.ResponseMapper<ArchiveUpdateResponse, ArchiveIdResponseVo>{
    override fun mapDtoToModel(type: ArchiveUpdateResponse?): ArchiveIdResponseVo {
        return type?.run {
            ArchiveIdResponseVo(
                archiveId = archiveId
            )
        }?: ArchiveIdResponseVo()
    }
}