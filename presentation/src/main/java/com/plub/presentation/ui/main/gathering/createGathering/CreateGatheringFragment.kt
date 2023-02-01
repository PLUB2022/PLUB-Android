package com.plub.presentation.ui.main.gathering.createGathering

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringBinding
import com.plub.presentation.ui.main.gathering.createGathering.adapter.FragmentCreateGatheringPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateGatheringFragment : BaseFragment<FragmentCreateGatheringBinding, com.plub.presentation.ui.main.gathering.createGathering.CreateGatheringPageState, com.plub.presentation.ui.main.gathering.createGathering.CreateGatheringViewModel>(
    FragmentCreateGatheringBinding::inflate
) {
    override val viewModel: com.plub.presentation.ui.main.gathering.createGathering.CreateGatheringViewModel by viewModels()
    private val pagerAdapter: FragmentCreateGatheringPagerAdapter by lazy {
        FragmentCreateGatheringPagerAdapter(this)
    }

    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.onBackPressed()
        }
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            viewPager.apply {
                isUserInputEnabled = false
                adapter = pagerAdapter
                ovalDotsIndicator.attachTo(this)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedDispatcher)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as com.plub.presentation.ui.main.gathering.createGathering.CreateGatheringEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: com.plub.presentation.ui.main.gathering.createGathering.CreateGatheringEvent) {
        when(event) {
            is com.plub.presentation.ui.main.gathering.createGathering.CreateGatheringEvent.NavigationPopEvent -> {
                findNavController().popBackStack()
            }
            else -> { }
        }
    }
}