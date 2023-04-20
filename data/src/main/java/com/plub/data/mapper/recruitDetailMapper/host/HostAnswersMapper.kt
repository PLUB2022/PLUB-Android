package com.plub.data.mapper.recruitDetailMapper.host

import com.plub.data.base.Mapper
import com.plub.data.dto.recruitDetail.host.AnswersDataResponse
import com.plub.domain.model.vo.home.recruitDetailVo.host.AnswersVo

object HostAnswersMapper : Mapper.ResponseMapper<AnswersDataResponse, AnswersVo> {
    override fun mapDtoToModel(type: AnswersDataResponse?): AnswersVo {
        return type?.run {
            AnswersVo(
                id = this.questionId,
                questions = this.question,
                answer = this.answer)
        }?: AnswersVo()
    }
}