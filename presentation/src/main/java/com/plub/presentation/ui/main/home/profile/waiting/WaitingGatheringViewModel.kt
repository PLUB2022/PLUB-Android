package com.plub.presentation.ui.main.home.profile.waiting

import com.plub.domain.model.enums.MyPageDetailViewType
import com.plub.domain.model.vo.myPage.MyPageDetailTitleVo
import com.plub.domain.model.vo.myPage.MyPageDetailVo
import com.plub.domain.model.vo.home.recruitdetailvo.host.AnswersVo
import com.plub.domain.model.vo.myPage.MyPageApplicationsVo
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.main.home.profile.recruiting.MyPageApplicantsGatheringState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WaitingGatheringViewModel @Inject constructor() :
    BaseViewModel<MyPageApplicantsGatheringState>(
        MyPageApplicantsGatheringState()
    ) {

    fun getPageDetail() {
        val list = arrayListOf(
            MyPageDetailVo(
                viewType = MyPageDetailViewType.TOP,
                title = MyPageDetailTitleVo(
                    title = "요란한 한줄",
                    date = arrayListOf("월", "화", "수", "목", "금", "토"),
                    time = "17:30",
                    position = "경기도 의정부시 동일로 474번길"
                )
            ),
            MyPageDetailVo(
                viewType = MyPageDetailViewType.MY_APPLICANTS_TEXT,
            ),
            MyPageDetailVo(
                viewType = MyPageDetailViewType.MY_APPLICATION,
                application = MyPageApplicationsVo(
                    profileImage = "https://plub.s3.ap-northeast-2.amazonaws.com/plubbing/mainImage/sports1.png",
                    name = "조경석",
                    date = "2001. 11. 06",
                    answerList = arrayListOf(
                        AnswersVo(
                            id = 1,
                            questions = "함께 하기 위한 질문",
                            answer = "안녕하세요 반가워여"
                        ),
                        AnswersVo(
                            id = 2,
                            questions = "함께 하기 위한 질문",
                            answer = "안녕하세요 반가워여"
                        ),
                        AnswersVo(
                            id = 3,
                            questions = "함께 하기 위한 질문",
                            answer = "안녕하세요 반가워여"
                        )
                    )
                )
            )

        )
        updateUiState { uiState ->
            uiState.copy(
                detailList = list
            )
        }
    }
}