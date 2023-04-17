package com.plub.data.dto.schedule

import com.google.gson.annotations.SerializedName

data class PutScheduleAttendRequest(
    @SerializedName("attendStatus")
    val attendStatus: String = "NO"
)