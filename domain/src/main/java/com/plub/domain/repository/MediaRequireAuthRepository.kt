package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.media.ChangeFileRequestVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import kotlinx.coroutines.flow.Flow

interface MediaRequireAuthRepository {
    suspend fun changeFile(request: ChangeFileRequestVo): Flow<UiState<UploadFileResponseVo>>

}