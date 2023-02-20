package com.plub.presentation.ui.main.home.categoryChoice.filter

import com.plub.domain.usecase.GetCategoriesUseCase
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.ui.PageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GatheringFilterViewModel @Inject constructor(
    val getCategoriesUseCase: GetCategoriesUseCase
): BaseViewModel<PageState.Default>(PageState.Default) {

}