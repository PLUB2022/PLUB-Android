package com.plub.data.api

import com.plub.data.base.ApiResponse
import com.plub.data.dto.bookmark.PlubBookmarkResponse
import com.plub.data.dto.plub.PlubCardListResponse
import retrofit2.Response
import retrofit2.http.*

interface BookmarkApi {
    companion object {
        private const val PATH_PLUBING_ID = "plubbingId"
        private const val QUERY_CURSOR_ID = "cursorId"
    }

    @POST(Endpoints.PLUBBING.BOOKMARK)
    suspend fun plubBookmark(@Path(PATH_PLUBING_ID) plubbingId:Int): Response<ApiResponse<PlubBookmarkResponse>>

    @GET(Endpoints.PLUBBING.BOOKMARK_ME)
    suspend fun getMyPlubBookmarks(
        @Query(QUERY_CURSOR_ID) cursorId: Int
    ): Response<ApiResponse<PlubCardListResponse>>
}