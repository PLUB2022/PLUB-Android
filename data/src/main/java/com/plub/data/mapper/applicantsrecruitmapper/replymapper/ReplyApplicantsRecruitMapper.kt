package com.plub.data.mapper.applicantsrecruitmapper.replymapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applicantsrecruit.reply.ReplyApplicantsRecruitResponse
import com.plub.domain.model.vo.home.applicantsrecruitvo.replyvo.ReplyApplicantsRecruitResponseVo

object ReplyApplicantsRecruitMapper : Mapper.ResponseMapper<ReplyApplicantsRecruitResponse, ReplyApplicantsRecruitResponseVo> {
    override fun mapDtoToModel(type: ReplyApplicantsRecruitResponse?): ReplyApplicantsRecruitResponseVo {
        return type?.run {
            ReplyApplicantsRecruitResponseVo(
                maxAccountNum = this.maxAccountNum,
                curAccountNum = this.curAccountNum
            )
        } ?: ReplyApplicantsRecruitResponseVo()
    }
}