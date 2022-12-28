package com.plub.data

import com.plub.domain.UiState
import com.plub.domain.base.DomainModel
import com.plub.domain.result.*

abstract class UiStateCallback<T> {
    abstract suspend fun onSuccess(state: UiState.Success<T>, customCode: Int)
    abstract suspend fun onError(state: UiState.Error)


    /**
     * 개별 api 실패처리가 있을 떄 람다 사용
     */
    protected fun <T> uiStateMapResult(
        uiState: UiState.Success<T>,
        individualFail: () -> IndividualFailure
    ): UiState<T> {
        return when (uiState.result) {
            is StateResult.Succeed,
            is CommonFailure -> uiState
            is IndividualFailure -> {
                uiState.apply {
                    result = individualFail.invoke()
                }
            }
        }
    }
}