package com.plub.data.mapper.applyrecruitmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plubJwt.applyrecruit.QuestionsResponse
import com.plub.data.dto.plubJwt.recruitdetail.host.Accounts
import com.plub.data.mapper.recruitdetailmapper.host.HostAnswersMapper
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.model.vo.home.recruitdetailvo.host.AccountsVo

object QuestionsRecruitMapper : Mapper.ResponseMapper<QuestionsResponse, QuestionsResponseVo> {
    override fun mapDtoToModel(type: QuestionsResponse?): QuestionsResponseVo {
        return type?.run {
            QuestionsResponseVo(
                questions.map {
                    QuestionMapper.mapDtoToModel(it)
                }
            )
        }?: QuestionsResponseVo(emptyList())
    }
}
