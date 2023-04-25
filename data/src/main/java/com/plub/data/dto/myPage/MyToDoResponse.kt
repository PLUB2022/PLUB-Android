package com.plub.data.dto.myPage

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.data.dto.plub.PlubInfoResponse
import com.plub.data.dto.todo.MyTodoTimelineResponse

data class MyToDoResponse(
    @SerializedName("plubbingInfo")
    val plubbingInfo : PlubInfoResponse = PlubInfoResponse(),
    @SerializedName("todoTimelineResponse")
    val myTodoTimelineResponse: MyTodoTimelineResponse = MyTodoTimelineResponse()
) : DataDto