package com.plub.presentation.ui.sign.signup

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentSignUpBinding
import com.plub.presentation.ui.sign.signup.adapter.FragmentSignUpPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpPageState, SignUpViewModel>(
    FragmentSignUpBinding::inflate
) {

    override val viewModel: SignUpViewModel by viewModels()

    private val pagerAdapter: FragmentSignUpPagerAdapter by lazy {
        FragmentSignUpPagerAdapter(this)
    }

    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.onBackPressed(binding.viewPager.currentItem)
        }
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            viewPager.apply {
                isUserInputEnabled = false
                adapter = pagerAdapter
                dotsIndicator.attachTo(this)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedDispatcher)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    binding.viewPager.currentItem = it.currentPage
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as SignUpEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: SignUpEvent) {
        when(event) {
            is SignUpEvent.GoToWelcome -> {
                val action = SignUpFragmentDirections.actionSignUpToWelcome()
                findNavController().navigate(action)
            }
            is SignUpEvent.NavigationPopEvent -> {
                findNavController().popBackStack()
            }
            is SignUpEvent.ShowSignUpErrorDialog -> {
                Toast.makeText(context, event.string, Toast.LENGTH_LONG).show()
            }
        }
    }
}