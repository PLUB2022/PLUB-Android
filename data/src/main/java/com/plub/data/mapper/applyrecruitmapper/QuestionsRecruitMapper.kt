package com.plub.data.mapper.applyrecruitmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applyrecruit.QuestionsResponse
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo

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