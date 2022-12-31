package com.plub.presentation.ui.sample

import androidx.fragment.app.viewModels
import com.plub.presentation.state.SampleLoginPageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentSampleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleFragment : BaseFragment<FragmentSampleBinding, SampleLoginPageState,SampleFragmentViewModel>(
    FragmentSampleBinding::inflate
) {

    override val viewModel: SampleFragmentViewModel by viewModels()

    override fun initView() {
        bindProgressBar(binding.loadingBar)
        binding.viewModel = viewModel
    }

    override fun initStates() {
        TODO("Not yet implemented")
    }
}