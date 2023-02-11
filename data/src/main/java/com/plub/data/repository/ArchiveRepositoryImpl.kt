package com.plub.data.repository

import com.plub.data.api.AccountApi
import com.plub.data.base.BaseRepository
import com.plub.domain.UiState
import com.plub.domain.model.vo.archive.ArchiveCardResponseVo
import com.plub.domain.model.vo.archive.ArchiveContentRequestVo
import com.plub.domain.model.vo.archive.DetailArchiveRequestVo
import com.plub.domain.repository.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArchiveRepositoryImpl @Inject constructor(private val accountApi: AccountApi) : ArchiveRepository, BaseRepository() {

    override suspend fun fetchAllArchive(request: Int): Flow<UiState<ArchiveCardResponseVo>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchDetailArchive(request: DetailArchiveRequestVo): Flow<UiState<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun createArchive(request: ArchiveContentRequestVo): Flow<UiState<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun reviseArchive(request: ArchiveContentRequestVo): Flow<UiState<Unit>> {
        TODO("Not yet implemented")
    }
}