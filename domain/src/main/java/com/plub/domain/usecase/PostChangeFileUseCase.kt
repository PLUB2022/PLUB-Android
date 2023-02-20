package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.media.ChangeFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostChangeFileUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
) : UseCase<ChangeFileRequestVo, Flow<UiState<UploadFileResponseVo>>>() {

    override suspend operator fun invoke(request: ChangeFileRequestVo): Flow<UiState<UploadFileResponseVo>> {
        return mediaRepository.changeFile(request)
    }
}