package com.plub.presentation.ui.sign.signup

import androidx.activity.OnBackPressedCallback
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.plub.domain.model.enums.SignUpPageType
import com.plub.domain.model.state.SignUpPageState
import com.plub.domain.model.vo.signUp.SignUpPageVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentSignUpBinding
import com.plub.presentation.ui.sign.signup.adapter.FragmentSignUpPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpPageState, SignUpViewModel>(
    FragmentSignUpBinding::inflate
) {

    interface Delegate{
        fun onMoveToNextPage(pageType: SignUpPageType, pageVo: SignUpPageVo)
    }

    override val viewModel: SignUpViewModel by viewModels()

    private val pagerAdapter: FragmentSignUpPagerAdapter by lazy {
        FragmentSignUpPagerAdapter(parentFragmentManager,lifecycle, object: Delegate {
            override fun onMoveToNextPage(pageType: SignUpPageType, pageVo: SignUpPageVo) {
                viewModel.onMoveToNextPage(pageType, pageVo)
            }
        })
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

        requireActivity().onBackPressedDispatcher.addCallback(backPressedDispatcher)
    }

    override fun initState() {
        super.initState()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    binding.viewPager.currentItem = it.currentPage
                }
            }
            launch {
                viewModel.navigationPop.collect {
                    findNavController().popBackStack()
                }
            }
            launch {
                viewModel.initPage.collect {
                    pagerAdapter.initPage(it.first, it.second)
                }
            }
        }
    }
}