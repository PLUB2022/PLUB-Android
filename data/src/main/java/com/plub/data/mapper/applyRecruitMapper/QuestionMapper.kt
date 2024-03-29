package com.plub.data.mapper.applyRecruitMapper

import com.plub.data.base.Mapper
import com.plub.data.dto.applyRecruit.QuestionDataResponse
import com.plub.domain.model.vo.home.applyVo.QuestionsDataVo

object QuestionMapper : Mapper.ResponseMapper<QuestionDataResponse, QuestionsDataVo> {
    override fun mapDtoToModel(type: QuestionDataResponse?): QuestionsDataVo {
        return type?.run {
            QuestionsDataVo(
                id = this.id,
                question = this.question
            )
        }?: QuestionsDataVo()
    }
}