package com.plub.presentation.ui.main.gathering.my.kickOut

import com.plub.domain.model.vo.account.AccountInfoVo
import com.plub.domain.model.vo.myGathering.MyGatheringResponseVo
import com.plub.domain.model.vo.myGathering.MyGatheringsResponseVo
import com.plub.presentation.ui.PageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class KickOutPageState(
    val members: StateFlow<List<AccountInfoVo>>
) : PageState