package com.plub.presentation.ui.createGathering.question

import com.plub.domain.model.state.PageState
import com.plub.domain.model.state.createGathering.CreateGatheringQuestionPageState
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGatheringQuestionViewModel @Inject constructor() :
BaseViewModel<CreateGatheringQuestionPageState>(CreateGatheringQuestionPageState()) {
}