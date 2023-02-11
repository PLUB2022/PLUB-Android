package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.archive.ArchiveIdResponseVo
import com.plub.domain.model.vo.archive.CreateArchiveRequestVo
import com.plub.domain.repository.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostCreateArchiveUseCase @Inject constructor(
    private val archiveRepository: ArchiveRepository
) : UseCase<CreateArchiveRequestVo, Flow<UiState<ArchiveIdResponseVo>>>() {
    override suspend operator fun invoke(request: CreateArchiveRequestVo): Flow<UiState<ArchiveIdResponseVo>> {
        return archiveRepository.createArchive(request)
    }
}