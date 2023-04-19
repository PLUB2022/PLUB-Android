package com.plub.data.repository

import com.plub.data.api.ArchiveApi
import com.plub.data.base.BaseRepository
import com.plub.data.dto.archive.ArchiveContentResponse
import com.plub.data.mapper.archiveMapper.*
import com.plub.domain.UiState
import com.plub.domain.model.vo.archive.*
import com.plub.domain.repository.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArchiveRepositoryImpl @Inject constructor(private val archiveApi: ArchiveApi) : ArchiveRepository, BaseRepository() {
    override suspend fun createArchive(request: CreateArchiveRequestVo): Flow<UiState<ArchiveIdResponseVo>> {
        val requestDto = ArchiveUpdateRequestMapper.mapModelToDto(request.body)
        return apiLaunch(archiveApi.createArchive(request.plubbingId, requestDto), ArchiveUpdateResponseMapper)
    }

    override suspend fun fetchAllArchive(request: BrowseAllArchiveRequestVo): Flow<UiState<ArchiveCardResponseVo>> {
        return apiLaunch(archiveApi.fetchAllArchives(request.plubbindId, request.cursorId), ArchivesResponseMapper)
    }

    override suspend fun fetchDetailArchive(request: DetailArchiveRequestVo): Flow<UiState<ArchiveDetailResponseVo>> {
        return apiLaunch(archiveApi.fetchDetailArchives(request.plubbingId, request.archiveId), ArchiveDetailResponseMapper)
    }

    override suspend fun editArchive(request: EditArchiveRequestVo): Flow<UiState<ArchiveContentResponseVo>> {
        val requestDto = ArchiveUpdateRequestMapper.mapModelToDto(request.body)
        return apiLaunch(archiveApi.editArchive(request.plubbingId, request.archiveId, requestDto), ArchiveContentResponseMapper)
    }

    override suspend fun deleteArchive(request: DetailArchiveRequestVo): Flow<UiState<ArchiveContentResponseVo>> {
        return apiLaunch(archiveApi.deleteArchive(request.plubbingId, request.archiveId), ArchiveContentResponseMapper)
    }
}