package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun uploadFile(request: UploadFileRequestVo): Flow<UiState<UploadFileResponseVo>>
}