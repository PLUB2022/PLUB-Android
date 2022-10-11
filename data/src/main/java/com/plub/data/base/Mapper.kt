package com.plub.data.base

import com.plub.data.UiStateCallback
import com.plub.domain.UiState
import com.plub.domain.base.DomainModel
import com.plub.domain.result.StateResult

abstract class Mapper<ENTITY:DataEntity, DOMAIN_MODEL:DomainModel> {
    abstract suspend fun mapFromEntity(type: ENTITY, result: UiStateCallback<DOMAIN_MODEL>)

    companion object {
        suspend fun <D : DomainModel> mapToUiState(customCode: Int, domainModel: D, result: UiStateCallback<D>) {
            val uiState = if(customCode == StateResult.SUCCEED_CODE) UiState.Success(domainModel, StateResult.Succeed)
            else UiState.Success(domainModel, StateResult.identifyFailure(customCode))
            result.onSuccess(uiState, customCode)
        }
    }
}