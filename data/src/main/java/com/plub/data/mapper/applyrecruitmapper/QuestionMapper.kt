package com.plub.data.mapper.applyrecruitmapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applyrecruit.Questions
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo

object QuestionMapper : Mapper.ResponseMapper<Questions, QuestionsDataVo> {
    override fun mapDtoToModel(type: Questions?): QuestionsDataVo {
        return type?.run {
            QuestionsDataVo(
                id = this.id,
                question = this.question
            )
        }?: QuestionsDataVo()
    }
}