package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.archive.ArchiveIdResponseVo
import com.plub.domain.model.vo.archive.DetailArchiveRequestVo
import com.plub.domain.repository.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteArchiveUseCase @Inject constructor(
    private val archiveRepository: ArchiveRepository
) : UseCase<DetailArchiveRequestVo, Flow<UiState<ArchiveIdResponseVo>>>() {
    override suspend operator fun invoke(request: DetailArchiveRequestVo): Flow<UiState<ArchiveIdResponseVo>> {
        return archiveRepository.deleteArchive(request)
    }
}