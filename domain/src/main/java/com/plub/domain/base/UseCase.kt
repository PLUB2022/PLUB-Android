package com.plub.domain.base

import com.plub.domain.UiState
import com.plub.domain.model.vo.login.SampleLogin
import kotlinx.coroutines.flow.Flow

abstract class UseCase<PARAM,MODEL:DomainModel> {

    abstract fun invoke(request:PARAM): Flow<UiState<MODEL>>
}