package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.archive.DetailArchiveRequestVo
import com.plub.domain.repository.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailArchiveUseCase @Inject constructor(
    private val archiveRepository: ArchiveRepository
) : UseCase<DetailArchiveRequestVo, Flow<UiState<Unit>>>(){
    override suspend fun invoke(request: DetailArchiveRequestVo): Flow<UiState<Unit>> {
        return archiveRepository.fetchDetailArchive(request)
    }
}