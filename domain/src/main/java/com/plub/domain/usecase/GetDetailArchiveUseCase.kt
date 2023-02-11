package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.archive.ArchiveDetailResponseVo
import com.plub.domain.model.vo.archive.DetailArchiveRequestVo
import com.plub.domain.repository.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailArchiveUseCase @Inject constructor(
    private val archiveRepository: ArchiveRepository
) : UseCase<DetailArchiveRequestVo, Flow<UiState<ArchiveDetailResponseVo>>>(){
    override suspend fun invoke(request: DetailArchiveRequestVo): Flow<UiState<ArchiveDetailResponseVo>> {
        return archiveRepository.fetchDetailArchive(request)
    }
}