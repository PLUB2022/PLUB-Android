package com.plub.data.dto.schedule

import com.google.gson.annotations.SerializedName
import com.plub.data.base.DataDto
import com.plub.domain.model.DomainModel

data class GetEntireScheduleResponse(
    @SerializedName("calendarList")
    val calendarList: CalendarList = CalendarList()
) : DataDto

data class CalendarList(
    @SerializedName("totalPages")
    val totalPages: Int = -1,
    @SerializedName("totalElements")
    val totalElements: Int = -1,
    @SerializedName("content")
    val content: List<Schedule> = emptyList(),
    @SerializedName("last")
    val last: Boolean = false,
)

data class Schedule(
    @SerializedName("calendarId")
    val calendarId: Int =  -1,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("memo")
    val memo: String = "",
    @SerializedName("startedAt")
    val startedAt: String = "",
    @SerializedName("endedAt")
    val endedAt: String = "",
    @SerializedName("startTime")
    val startTime: String = "",
    @SerializedName("endTime")
    val endTime: String = "",
    @SerializedName("isAllDay")
    val isAllDay: Boolean = false,
    @SerializedName("address")
    val address: String = "",
    @SerializedName("roadAddress")
    val roadAddress: String = "",
    @SerializedName("placeName")
    val placeName: String = "",
    @SerializedName("alarmType")
    val alarmType: String = "",
    @SerializedName("calendarAttendList")
    val calendarAttendList: CalendarAttendList = CalendarAttendList()
)

data class CalendarAttendList(
    @SerializedName("calendarAttendList")
    val calendarAttendList: List<CalendarAttend> = emptyList()
)

data class CalendarAttend(
    @SerializedName("calendarAttendId")
    val calendarAttendId: Int = -1,
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("profileImage")
    val profileImage: String = "",
    @SerializedName("AttendStatus")
    val AttendStatus: String = ""
)