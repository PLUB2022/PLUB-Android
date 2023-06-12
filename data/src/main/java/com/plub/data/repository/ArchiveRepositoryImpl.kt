package com.plub.data.repository

import com.plub.data.api.ArchiveApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.archiveMapper.*
import com.plub.domain.UiState
import com.plub.domain.error.ArchiveError
import com.plub.domain.model.vo.archive.*
import com.plub.domain.repository.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArchiveRepositoryImpl @Inject constructor(private val archiveApi: ArchiveApi) : ArchiveRepository, BaseRepository() {
    override suspend fun createArchive(request: CreateArchiveRequestVo): Flow<UiState<ArchiveIdResponseVo>> {
        val requestDto = ArchiveUpdateRequestMapper.mapModelToDto(request.body)
        return apiLaunch(apiCall = { archiveApi.createArchive(request.plubbingId, requestDto) }, ArchiveUpdateResponseMapper){
            ArchiveError.make(it)
        }
    }

    override suspend fun fetchAllArchive(request: BrowseAllArchiveRequestVo): Flow<UiState<ArchiveCardResponseVo>> {
        return apiLaunch(apiCall = { archiveApi.fetchAllArchives(request.plubbindId, request.cursorId) }, ArchivesResponseMapper){
            ArchiveError.make(it)
        }
    }

    override suspend fun fetchDetailArchive(request: DetailArchiveRequestVo): Flow<UiState<ArchiveDetailResponseVo>> {
        return apiLaunch(apiCall = { archiveApi.fetchDetailArchives(request.plubbingId, request.archiveId) }, ArchiveDetailResponseMapper){
            ArchiveError.make(it)
        }
    }

    override suspend fun editArchive(request: EditArchiveRequestVo): Flow<UiState<ArchiveContentResponseVo>> {
        val requestDto = ArchiveUpdateRequestMapper.mapModelToDto(request.body)
        return apiLaunch(apiCall = { archiveApi.editArchive(request.plubbingId, request.archiveId, requestDto) }, ArchiveContentResponseMapper){
            ArchiveError.make(it)
        }
    }

    override suspend fun deleteArchive(request: DetailArchiveRequestVo): Flow<UiState<ArchiveContentResponseVo>> {
        return apiLaunch(apiCall = { archiveApi.deleteArchive(request.plubbingId, request.archiveId) }, ArchiveContentResponseMapper){
            ArchiveError.make(it)
        }
    }
}