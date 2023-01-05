package com.plub.presentation.ui.createGathering.peopleNumber

import androidx.fragment.app.viewModels
import com.plub.domain.model.state.PageState
import com.plub.domain.model.state.createGathering.CreateGatheringPeopleNumberPageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringPeopleNumberBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGatheringPeopleNumberFragment :
    BaseFragment<FragmentCreateGatheringPeopleNumberBinding, CreateGatheringPeopleNumberPageState, CreateGatheringPeopleNumberViewModel>(
        FragmentCreateGatheringPeopleNumberBinding::inflate
    ) {
    override val viewModel: CreateGatheringPeopleNumberViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({requireParentFragment()})

    override fun initView() {
        with(binding) {
            vm = viewModel
            parentVm = parentViewModel
        }
    }
}