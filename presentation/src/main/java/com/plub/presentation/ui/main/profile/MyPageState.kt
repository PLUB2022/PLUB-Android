package com.plub.presentation.ui.main.profile

import com.plub.domain.model.vo.myPage.MyPageVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.StateFlow

data class MyPageState(
    val myPageVo : StateFlow<List<MyPageVo>>,
) : PageState
