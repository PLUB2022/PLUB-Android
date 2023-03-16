package com.plub.domain.model.vo.myPage

import com.plub.domain.model.vo.home.recruitdetailvo.host.AnswersVo

data class MyPageMyApplicationVo(
    val recruitDate : String = "",
    val titleVo: MyPageDetailTitleVo = MyPageDetailTitleVo(),
    val answerList : List<AnswersVo> = emptyList()
)