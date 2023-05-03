package com.plub.presentation.ui.main.gathering.create.peopleNumber

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringPeopleNumberBinding
import com.plub.presentation.ui.main.gathering.create.CreateGatheringViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateGatheringPeopleNumberFragment :
    BaseFragment<FragmentCreateGatheringPeopleNumberBinding, CreateGatheringPeopleNumberPageState, CreateGatheringPeopleNumberViewModel>(
        FragmentCreateGatheringPeopleNumberBinding::inflate
    ) {
    override val viewModel: CreateGatheringPeopleNumberViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({ requireParentFragment() })

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
                parentViewModel.childrenPageStateFlow.collect { pageState ->
                    viewModel.initUiState(pageState)
                }
            }

            launch {
                parentViewModel.eventFlow.collect {
                    if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@collect

                    when (it) {
                        is com.plub.presentation.ui.main.gathering.create.CreateGatheringEvent.GoToPrevPage -> {
                            parentViewModel.setChildrenPageState(viewModel.uiState.value)
                            parentViewModel.goToPrevPageAndEmitChildrenPageState()
                        }
                    }
                }
            }
        }
    }
}