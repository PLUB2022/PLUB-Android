package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.common.HobbyVo
import com.plub.domain.repository.HobbyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllHobbiesUseCase @Inject constructor(
    private val hobbyRepository: HobbyRepository
) : UseCase<Unit, Flow<UiState<List<HobbyVo>>>>() {
    override suspend operator fun invoke(request: Unit): Flow<UiState<List<HobbyVo>>> = flow {
        hobbyRepository.allHobbies().collect{ emit(it) }
    }
}