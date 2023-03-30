package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.repository.ApplicantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteMyApplicationUseCase @Inject constructor(
    private val applicantsRepository: ApplicantsRepository
) : UseCase<Int, Flow<UiState<Int>>>() {
    override suspend operator fun invoke(request: Int): Flow<UiState<Int>> {
        return applicantsRepository.deleteMyApplication(request)
    }
}