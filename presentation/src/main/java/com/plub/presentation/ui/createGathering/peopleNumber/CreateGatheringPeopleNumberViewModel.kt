package com.plub.presentation.ui.createGathering.peopleNumber

import com.plub.domain.model.state.PageState
import com.plub.domain.model.state.createGathering.CreateGatheringPeopleNumberPageState
import com.plub.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateGatheringPeopleNumberViewModel @Inject constructor() :
    BaseViewModel<CreateGatheringPeopleNumberPageState>(CreateGatheringPeopleNumberPageState()) {

}