package com.plub.presentation.ui.createGathering.peopleNumber

import androidx.fragment.app.viewModels
import com.plub.presentation.state.createGathering.CreateGatheringPeopleNumberPageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringPeopleNumberBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                if (viewModel.uiState.value != CreateGatheringPeopleNumberPageState())
                    return@launch

                parentViewModel.childrenPageStateFlow.collect { pageState ->
                    if (pageState is CreateGatheringPeopleNumberPageState)
                        viewModel.initUiState(pageState)
                }
            }
        }
    }
}