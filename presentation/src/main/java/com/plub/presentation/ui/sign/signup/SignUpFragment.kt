package com.plub.presentation.ui.sign.signup

import androidx.fragment.app.viewModels
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
        FragmentSignUpPagerAdapter(this, object: Delegate {
            override fun onMoveToNextPage(pageType: SignUpPageType, pageVo: SignUpPageVo) {
                viewModel.onMoveToNextPage(pageType, pageVo)
            }
        })
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
    }

    override fun initState() {
        super.initState()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    binding.viewPager.currentItem = it.currentPage
                }
            }
        }
    }
}