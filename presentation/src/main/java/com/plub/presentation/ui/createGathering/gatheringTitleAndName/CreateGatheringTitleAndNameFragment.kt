package com.plub.presentation.ui.createGathering.gatheringTitleAndName

import androidx.fragment.app.viewModels
import com.plub.domain.model.state.CreateGatheringTitleAndNamePageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringTitleAndNameBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateGatheringTitleAndNameFragment : BaseFragment<FragmentCreateGatheringTitleAndNameBinding, CreateGatheringTitleAndNamePageState, CreateGatheringTitleAndNameViewModel>(
    FragmentCreateGatheringTitleAndNameBinding::inflate
) {

    override val viewModel: CreateGatheringTitleAndNameViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({requireParentFragment()})

    override fun initView() {
        binding.apply {
            vm = viewModel
            parentVm = parentViewModel
        }
    }

    override fun initState() {
        super.initState()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                if(viewModel.uiState.value != CreateGatheringTitleAndNamePageState())
                    return@launch

                parentViewModel.childrenPageStateFlow.collect {
                    if(it is CreateGatheringTitleAndNamePageState)
                        viewModel.initUiState(it)
                }
            }
        }
    }
}