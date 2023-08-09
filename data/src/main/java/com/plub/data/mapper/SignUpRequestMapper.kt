package com.plub.data.mapper

import com.plub.data.base.Mapper
import com.plub.data.dto.signUp.SignUpRequest
import com.plub.data.util.DateFormatUtil
import com.plub.domain.model.enums.Gender
import com.plub.domain.model.vo.signUp.SignUpRequestVo
import java.util.*

object SignUpRequestMapper: Mapper.RequestMapper<SignUpRequest, SignUpRequestVo> {
    override fun mapModelToDto(type: SignUpRequestVo): SignUpRequest {
        return type.run {
            val birthday = personalInfoVo.calendar?: Calendar.getInstance()
            val gender = personalInfoVo.gender ?: Gender.MAN
            SignUpRequest(
                signToken = signToken,
                fcmToken = fcmToken,
                categoryList = hobbyInfoVo.hobbies.map { it.subId },
                profileImage = profileUrl,
                birthday = DateFormatUtil.getSignUpBirthday(birthday),
                gender = gender.value,
                introduce = profileComposeVo.introduce,
                nickname = profileComposeVo.nickname,
                marketPolicy = termsPageVo.marketing,
                personalPolicy = termsPageVo.collect,
                placePolicy = termsPageVo.location,
                usePolicy = termsPageVo.privacy,
                agePolicy = termsPageVo.age,
            )
        }
    }
}