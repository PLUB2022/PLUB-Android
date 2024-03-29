package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.archive.ArchiveContentResponseVo
import com.plub.domain.model.vo.archive.ArchiveIdResponseVo
import com.plub.domain.model.vo.archive.EditArchiveRequestVo
import com.plub.domain.repository.ArchiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PutEditArchiveUseCase @Inject constructor(
    private val archiveRepository: ArchiveRepository
) : UseCase<EditArchiveRequestVo, Flow<UiState<ArchiveContentResponseVo>>>() {
    override suspend operator fun invoke(request: EditArchiveRequestVo): Flow<UiState<ArchiveContentResponseVo>> {
        return archiveRepository.editArchive(request)
    }
}