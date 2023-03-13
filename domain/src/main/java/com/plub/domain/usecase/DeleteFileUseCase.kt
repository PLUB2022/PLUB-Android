package com.plub.domain.usecase

import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.media.DeleteFileRequestVo
import com.plub.domain.repository.MediaRepository
import javax.inject.Inject

class DeleteFileUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
) : UseCase<DeleteFileRequestVo, String>() {

    override suspend operator fun invoke(request: DeleteFileRequestVo): String {
        return mediaRepository.deleteFile(request)
    }
}