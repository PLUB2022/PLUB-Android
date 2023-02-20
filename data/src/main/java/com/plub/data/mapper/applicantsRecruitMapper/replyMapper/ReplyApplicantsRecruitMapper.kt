package com.plub.data.mapper.applicantsRecruitMapper.replyMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applicantsRecruit.reply.ReplyApplicantsRecruitResponse
import com.plub.domain.model.vo.home.applicantsRecruitVo.replyVo.ReplyApplicantsRecruitResponseVo

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