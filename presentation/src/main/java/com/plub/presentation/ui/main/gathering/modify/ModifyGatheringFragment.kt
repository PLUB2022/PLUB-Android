package com.plub.presentation.ui.main.gathering.modify

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.plub.domain.model.enums.ToastType
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentModifyGatheringBinding
import com.plub.presentation.util.PlubToast
import com.plub.presentation.util.onThrottleClick
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

        viewModel.getGatheringInfoDetail(navArgs.plubingId) {
            viewModel.getQuestions()
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
                        is ModifyGatheringEvent.GoToModifyInfo -> {
                            val action = ModifyGatheringFragmentDirections.actionModifyGatheringToModifyInfo(navArgs.plubingId, event.data)
                            findNavController().navigate(action)
                        }
                        is ModifyGatheringEvent.ShowPullUpSuccessToastMsg -> {
                            PlubToast.createToast(ToastType.COMPLETE, requireContext().applicationContext, getString(
                                R.string.modify_gathering_pull_up_success)).show()
                        }
                    }
                }
            }

        }
    }
}