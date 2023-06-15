package com.plub.presentation.ui.main.gathering.my.kickOut

import androidx.lifecycle.viewModelScope
import com.plub.domain.error.GatheringError
import com.plub.domain.model.vo.account.AccountInfoVo
import com.plub.domain.model.vo.myGathering.KickOutRequestVo
import com.plub.domain.usecase.DeleteKickOutMemberUseCase
import com.plub.domain.usecase.GetGatheringMembersUseCase
import com.plub.presentation.base.BaseTestViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KickOutViewModel @Inject constructor(
    private val getMyGatheringMembersUseCase: GetGatheringMembersUseCase,
    private val deleteKickOutMemberUseCase: DeleteKickOutMemberUseCase
) : BaseTestViewModel<KickOutPageState>() {

    private val _members: MutableStateFlow<List<AccountInfoVo>> = MutableStateFlow(
        emptyList()
    )

    override val uiState: KickOutPageState = KickOutPageState(
        members = _members.asStateFlow(),
    )

    fun getMembers(plubingId: Int) = viewModelScope.launch {
        getMyGatheringMembersUseCase(plubingId).collect { uiState ->
            inspectUiState(uiState, ::handleSuccessGetMembers, {_, individual ->
                handleGatheringError(individual as GatheringError)
            })
        }
    }

    private fun handleGatheringError(gatheringError: GatheringError){
        when(gatheringError){
            is GatheringError.NotHost -> TODO()
            is GatheringError.NotMemberPlubbing -> TODO()
            else -> TODO()
        }
    }

    private fun handleSuccessGetMembers(data: List<AccountInfoVo>) {
        _members.update { data }
    }

    fun kickOutMember(plubingId: Int, accountId: Int) = viewModelScope.launch {
        deleteKickOutMemberUseCase(KickOutRequestVo(plubingId, accountId)).collect { uiState ->
            inspectUiState(uiState, succeedCallback = { handleSuccessKickOutMember(accountId) }, individualErrorCallback = {_, individual ->
                handleGatheringError(individual as GatheringError)
            })
        }
    }

    private fun handleSuccessKickOutMember(accountId: Int) {
        _members.update { members ->
            val accountInfoVoToRemove = members.find { it.userId == accountId } ?: return
            members.minus(accountInfoVoToRemove)
        }
    }

    fun goToBack() {
        emitEventFlow(
            KickOutEvent.GoToBack
        )
    }
}