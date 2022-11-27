package com.plub.data.util

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

data class ApiResponse<D:DataDto>(
    @SerializedName("data")
    val data:D? = null,
    @SerializedName("message")
    val message:String = "",
    @SerializedName("statusCode")
    val statusCode:Int = -1,
)