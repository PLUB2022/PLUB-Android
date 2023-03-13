package com.plub.data.dto.schedule

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto

data class CreateScheduleRequest(
    @SerializedName("title")
    val title: String,
    @SerializedName("memo")
    val memo: String,
    @SerializedName("startedAt")
    val startedAt: String?,
    @SerializedName("endedAt")
    val endedAt: String?,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("isAllDay")
    val isAllDay: Boolean,
    @SerializedName("address")
    val address: String?,
    @SerializedName("roadAddress")
    val roadAddress: String?,
    @SerializedName("placeName")
    val placeName: String?,
    @SerializedName("alarmType")
    val alarmType: String
): DataDto