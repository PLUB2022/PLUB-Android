package com.plub.data.mapper.recruitdetailmapper.host

import com.plub.data.base.Mapper
import com.plub.data.dto.recruitdetail.host.AnswersDataResponse
import com.plub.domain.model.vo.home.recruitdetailvo.host.AnswersVo

object HostAnswersMapper : Mapper.ResponseMapper<AnswersDataResponse, AnswersVo> {
    override fun mapDtoToModel(type: AnswersDataResponse?): AnswersVo {
        return type?.run {
            AnswersVo(
                questions = this.question,
                answer = this.answer)
        }?: AnswersVo()
    }
}