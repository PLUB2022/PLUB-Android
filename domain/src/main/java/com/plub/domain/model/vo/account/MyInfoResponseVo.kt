package com.plub.domain.model.vo.account

import com.plub.domain.model.DomainModel

data class MyInfoResponseVo(
    val email: String = "",
    val nickname: String = "",
    val socialType: String = "",
    val birthday: String = "",
    val gender: String = "",
    val introduce: String = "",
    val profileImage: String? = "",
    val isReceivedPushNotification : Boolean = false
): DomainModel