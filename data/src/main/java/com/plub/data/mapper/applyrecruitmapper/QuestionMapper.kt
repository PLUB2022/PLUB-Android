package com.plub.data.mapper.applyrecruitmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.plubJwt.applyrecruit.Questions
import com.plub.data.dto.plubJwt.applyrecruit.QuestionsResponse
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo

object QuestionMapper : Mapper.ResponseMapper<Questions, QuestionsDataVo> {
    override fun mapDtoToModel(type: Questions?): QuestionsDataVo {
        return type?.run {
            QuestionsDataVo(
                id, question
            )
        }?: QuestionsDataVo(0, "")
    }
}