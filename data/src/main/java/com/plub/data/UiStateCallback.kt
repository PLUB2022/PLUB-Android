package com.plub.data

import com.plub.domain.UiState
import com.plub.domain.base.DomainModel
import com.plub.domain.result.CommonFailure
import com.plub.domain.result.IndividualFailure
import com.plub.domain.result.StateResult

abstract class UiStateCallback<T : DomainModel> {
    abstract suspend fun onSuccess(state: UiState.Success<T>, customCode: Int)


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

    /**
     * 개별 api 실패처리가 없을 떄 사용
     * 사실상 함수 호출 없이 return uiState 해버리면 되나
     * 통일성을 위해 오버로딩
     */
    protected fun <T> uiStateMapResult(
        uiState: UiState.Success<T>
    ): UiState<T> {
        return uiState
    }
}