package com.plub.data.api

import com.plub.data.dto.bookmark.PlubBookmarkResponse
import com.plub.data.util.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface BookmarkApi {
    @POST(Endpoints.PLUBBING.BOOKMARK)
    suspend fun plubBookmark(@Path("plubbingId") plubbingId:Int): Response<ApiResponse<PlubBookmarkResponse>>
}