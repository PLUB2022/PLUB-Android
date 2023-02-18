package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.media.ChangeFileRequestVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.repository.MediaRepository
import com.plub.domain.repository.MediaRequireAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostChangeFileUseCase @Inject constructor(
    private val mediaRepository: MediaRequireAuthRepository,
) : UseCase<ChangeFileRequestVo, Flow<UiState<UploadFileResponseVo>>>() {

    override suspend operator fun invoke(request: ChangeFileRequestVo): Flow<UiState<UploadFileResponseVo>> {
        return mediaRepository.changeFile(request)
    }
}