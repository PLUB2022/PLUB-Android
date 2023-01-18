package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostUploadFileUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
) : UseCase<UploadFileRequestVo, Flow<UiState<UploadFileResponseVo>>>() {
    override suspend operator fun invoke(request: UploadFileRequestVo): Flow<UiState<UploadFileResponseVo>> {
        return mediaRepository.uploadFile(request)
    }
}