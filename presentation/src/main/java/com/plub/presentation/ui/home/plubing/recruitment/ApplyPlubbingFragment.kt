package com.plub.presentation.ui.home.plubing.recruitment

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentApplyPlubbingBinding
import com.plub.presentation.state.PageState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApplyPlubbingFragment : BaseFragment<FragmentApplyPlubbingBinding, PageState.Default, ApplyPlubbingViewModel>(
    FragmentApplyPlubbingBinding::inflate
)  {
    override val viewModel: ApplyPlubbingViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            //TODO 할 일
        }
    }

    override fun initStates() {
        //TODO("Not yet implemented")
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                //TODO 할일
            }

        }
    }



}