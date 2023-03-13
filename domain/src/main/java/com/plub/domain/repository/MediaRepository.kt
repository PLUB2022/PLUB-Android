package com.plub.domain.repository

import com.plub.domain.UiState
import com.plub.domain.model.vo.media.ChangeFileRequestVo
import com.plub.domain.model.vo.media.DeleteFileRequestVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun uploadFile(request: UploadFileRequestVo): Flow<UiState<UploadFileResponseVo>>
    suspend fun deleteFile(request: DeleteFileRequestVo): Flow<UiState<String>>
    suspend fun changeFile(request: ChangeFileRequestVo): Flow<UiState<UploadFileResponseVo>>
}