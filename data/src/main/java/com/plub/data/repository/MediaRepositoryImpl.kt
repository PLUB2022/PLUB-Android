package com.plub.data.repository

import com.plub.data.api.MediaApi
import com.plub.data.api.MediaRequireAuthApi
import com.plub.data.base.BaseRepository
import com.plub.data.mapper.UnitResponseMapper
import com.plub.data.mapper.UploadFileResponseMapper
import com.plub.data.util.FormDataUtil
import com.plub.domain.UiState
import com.plub.domain.model.vo.media.ChangeFileRequestVo
import com.plub.domain.model.vo.media.DeleteFileRequestVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaApi: MediaApi,
    private val mediaRequireAuthApi: MediaRequireAuthApi
) : MediaRepository, BaseRepository() {

    companion object {
        private const val KEY_TYPE = "type"
        private const val KEY_FILES = "files"
        private const val KEY_NEW_FILES = "newFiles"
        private const val TO_DELETE_URLS = "toDeleteUrls"
    }

    override suspend fun uploadFile(request: UploadFileRequestVo): Flow<UiState<UploadFileResponseVo>> {
        val typeBody = FormDataUtil.getBody(KEY_TYPE, request.type.type)
        val fileBody = FormDataUtil.getImageBody(KEY_FILES, request.file)
        return apiLaunch(mediaApi.uploadFile(typeBody, fileBody), UploadFileResponseMapper)
    }

    override suspend fun deleteFile(request: DeleteFileRequestVo): Flow<UiState<Unit>> {
        return apiLaunch(mediaRequireAuthApi.deleteFile(request.type.type, request.file), UnitResponseMapper)
    }

    override suspend fun changeFile(request: ChangeFileRequestVo): Flow<UiState<UploadFileResponseVo>> {
        val typeBody = FormDataUtil.getBody(KEY_TYPE, request.type.type)
        val toDeleteUrlsBody = FormDataUtil.getBody(TO_DELETE_URLS, request.toDeleteUrls)
        val fileBody = FormDataUtil.getImageBody(KEY_NEW_FILES, request.file)
        return apiLaunch(mediaRequireAuthApi.changeFile(typeBody, toDeleteUrlsBody, fileBody), UploadFileResponseMapper)
    }
}