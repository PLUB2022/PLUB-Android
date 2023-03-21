package com.plub.data.mapper.applyrecruitmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applyRecruit.QuestionsListResponse
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo

object QuestionsRecruitMapper : Mapper.ResponseMapper<QuestionsListResponse, QuestionsResponseVo> {
    override fun mapDtoToModel(type: QuestionsListResponse?): QuestionsResponseVo {
        return type?.run {
            QuestionsResponseVo(
                questions = this.questions.map {
                    QuestionMapper.mapDtoToModel(it)
                }
            )
        }?: QuestionsResponseVo()
    }
}