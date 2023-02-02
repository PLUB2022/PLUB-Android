package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.archive.ArchiveContentRequestVo
import com.plub.domain.model.vo.archive.DetailArchiveRequestVo
import kotlinx.coroutines.flow.Flow

interface ArchiveRepository {
    suspend fun fetchAllArchive(request : Int) : Flow<UiState<Unit>>
    suspend fun fetchDetailArchive(request: DetailArchiveRequestVo) : Flow<UiState<Unit>>
    suspend fun createArchive(request: ArchiveContentRequestVo) : Flow<UiState<Unit>>
    suspend fun reviseArchive(request: ArchiveContentRequestVo) : Flow<UiState<Unit>>
}