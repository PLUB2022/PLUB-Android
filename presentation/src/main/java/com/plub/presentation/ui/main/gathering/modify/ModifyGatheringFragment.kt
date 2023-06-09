package com.plub.presentation.ui.main.gathering.modify

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentModifyGatheringBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModifyGatheringFragment : BaseFragment
<FragmentModifyGatheringBinding, ModifyGatheringPageState, ModifyGatheringViewModel>(
    FragmentModifyGatheringBinding::inflate
) {
    override val viewModel: ModifyGatheringViewModel by viewModels()
    private val navArgs: ModifyGatheringFragmentArgs by navArgs()


    override fun initView() {
        binding.apply {
            vm = viewModel
        }

        viewModel.getGatheringInfoDetail(navArgs.plubingId) { id ->
            viewModel.getQuestions(id)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {

            launch {
                viewModel.eventFlow.collect { event ->
                    when(event) {
                        is ModifyGatheringEvent.GoToBack -> findNavController().popBackStack()
                        is ModifyGatheringEvent.GoToModifyQuestion -> {
                            val action = ModifyGatheringFragmentDirections.actionModifyGatheringToModifyQuestion(navArgs.plubingId, event.data)
                            findNavController().navigate(action)
                        }
                        is ModifyGatheringEvent.GoToModifyRecruit -> {
                            val action = ModifyGatheringFragmentDirections.actionModifyGatheringToModifyRecruit(navArgs.plubingId, event.data)
                            findNavController().navigate(action)
                        }
                    }
                }
            }

        }
    }
}