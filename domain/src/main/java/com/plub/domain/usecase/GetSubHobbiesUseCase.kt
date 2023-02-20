package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.common.SubHobbyVo
import com.plub.domain.repository.HobbyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSubHobbiesUseCase @Inject constructor(
    private val hobbyRepository: HobbyRepository
) : UseCase<Int, Flow<UiState<List<SubHobbyVo>>>>() {
    override suspend operator fun invoke(request: Int): Flow<UiState<List<SubHobbyVo>>> {
        return hobbyRepository.subHobbies(request)
    }
}