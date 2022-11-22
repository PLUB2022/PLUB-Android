package com.plub.presentation.ui.sign.signup

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.plub.domain.model.state.PageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentSignUpBinding
import com.plub.presentation.ui.sign.signup.adapter.FragmentSignUpPagerAdapter
import com.plub.presentation.ui.sign.terms.TermsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, PageState.Default, SignUpViewModel>(
    FragmentSignUpBinding::inflate
) {

    override val viewModel: SignUpViewModel by viewModels()
    private val pagerAdapter: FragmentSignUpPagerAdapter by lazy {
        FragmentSignUpPagerAdapter(this)
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

            }
        }
    }
}