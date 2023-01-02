package com.plub.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.plub.data.api.KakaoLocationApi
import com.plub.data.dto.kakaoLocation.KakaoLocationInfoDocument
import com.plub.data.mapper.KakaoLocationDocumentsMapper
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoDocumentVo
import com.plub.domain.model.vo.kakaoLocation.KakaoLocationInfoVo
import java.lang.Exception

class KakaoLocationPagingSource(
    private val api: KakaoLocationApi,
    private val query: String
): PagingSource<Int, KakaoLocationInfoDocumentVo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, KakaoLocationInfoDocumentVo> {
        val page = params.key ?: 1
        return try  {
            val response = api.fetchLocationInfo(query, page)
            LoadResult.Page(
                data = KakaoLocationDocumentsMapper.mapDtoToModel(response).documents,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.documents.isEmpty() || response.meta.isEnd) null else page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, KakaoLocationInfoDocumentVo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}