package com.plub.presentation.ui.createGathering

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringBinding
import com.plub.presentation.event.SignUpEvent
import com.plub.presentation.ui.createGathering.adapter.FragmentCreateGatheringPagerAdapter
import com.plub.presentation.ui.sign.signup.SignUpFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateGatheringFragment : BaseFragment<FragmentCreateGatheringBinding, CreateGatheringPageState, CreateGatheringViewModel>(
    FragmentCreateGatheringBinding::inflate
) {
    override val viewModel: CreateGatheringViewModel by viewModels()
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
                    inspectEventFlow(it as CreateGatheringEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: CreateGatheringEvent) {
        when(event) {
            is CreateGatheringEvent.NavigationPopEvent -> {
                findNavController().popBackStack()
            }
            else -> { }
        }
    }
}