package com.plub.presentation.ui.main.gathering.modifyGathering.recruit

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.domain.usecase.GetRecruitDetailUseCase
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyRecruitViewModel @Inject constructor(
    private val getRecruitDetailUseCase: GetRecruitDetailUseCase
) : BaseViewModel<ModifyRecruitPageState>(ModifyRecruitPageState()) {



}