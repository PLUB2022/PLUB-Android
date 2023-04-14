package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.media.DeleteFileRequestVo
import com.plub.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFileUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
) : UseCase<DeleteFileRequestVo, Flow<UiState<Unit>>>() {

    override suspend operator fun invoke(request: DeleteFileRequestVo): Flow<UiState<Unit>> {
        return mediaRepository.deleteFile(request)
    }
}