package com.plub.data.repository

import com.plub.data.api.MediaApi
import com.plub.data.api.MediaRequireAuthApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.UploadFileResponseMapper
import com.plub.data.util.FormDataUtil
import com.plub.domain.UiState
import com.plub.domain.model.vo.media.ChangeFileRequestVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.repository.MediaRepository
import com.plub.domain.repository.MediaRequireAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRequireAuthRepositoryImpl @Inject constructor(private val mediaApi: MediaRequireAuthApi) : MediaRequireAuthRepository, BaseRepository() {

    companion object {
        private const val KEY_TYPE = "type"
        private const val KEY_FILES = "newFiles"
        private const val TO_DELETE_URLS = "toDeleteUrls"
    }

    override suspend fun changeFile(request: ChangeFileRequestVo): Flow<UiState<UploadFileResponseVo>> {
        val typeBody = FormDataUtil.getBody(KEY_TYPE, request.type.type)
        val toDeleteUrlsBody = FormDataUtil.getBody(TO_DELETE_URLS, request.toDeleteUrls)
        val fileBody = FormDataUtil.getImageBody(KEY_FILES, request.file)
        return apiLaunch(mediaApi.changeFile(typeBody, toDeleteUrlsBody, fileBody), UploadFileResponseMapper)
    }
}