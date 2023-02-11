package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.archive.*
import kotlinx.coroutines.flow.Flow

interface ArchiveRepository {
    suspend fun createArchive(request: CreateArchiveRequestVo) : Flow<UiState<ArchiveIdResponseVo>>
    suspend fun fetchAllArchive(request : BrowseAllArchiveRequestVo) : Flow<UiState<ArchiveCardResponseVo>>
    suspend fun fetchDetailArchive(request: DetailArchiveRequestVo) : Flow<UiState<ArchiveDetailResponseVo>>
    suspend fun editArchive(request: EditArchiveRequestVo) : Flow<UiState<ArchiveIdResponseVo>>
    suspend fun deleteArchive(request: DetailArchiveRequestVo) : Flow<UiState<ArchiveIdResponseVo>>
}