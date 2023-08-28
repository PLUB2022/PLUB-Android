package com.plub.domain.usecase

import com.plub.domain.UiState
import com.plub.domain.base.UseCase
import com.plub.domain.model.vo.home.categoryResponseVo.CategoryDefaultImageRequestVo
import com.plub.domain.model.vo.home.categoryResponseVo.CategoryDefaultImageResponseVo
import com.plub.domain.repository.HobbyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryDefaultImageUseCase @Inject constructor(
    private val hobbyRepository: HobbyRepository,
) : UseCase<CategoryDefaultImageRequestVo, Flow<UiState<CategoryDefaultImageResponseVo>>>(){
    override suspend fun invoke(requst : CategoryDefaultImageRequestVo): Flow<UiState<CategoryDefaultImageResponseVo>> {
        return hobbyRepository.getCategoryDefaultImage(requst.categoryId, requst.subCategoryId)
    }
}