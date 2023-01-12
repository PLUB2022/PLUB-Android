package com.plub.presentation.ui.home.plubing.recruitment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.plub.domain.model.state.SampleHomeState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentDetailRecruitmentPlubingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecruitmentFragment : BaseFragment<FragmentDetailRecruitmentPlubingBinding, SampleHomeState, RecruitmentViewModel>(
    FragmentDetailRecruitmentPlubingBinding::inflate
)  {
    override val viewModel: RecruitmentViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            //TODO 할 일
        }
    }

    override fun initStates() {
        //TODO("Not yet implemented")
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.goToApplyFragment.collect{
                    goToApplyPlubbingFragment()
                }
            }

        }
    }

    fun goToApplyPlubbingFragment(){
        val action = RecruitmentFragmentDirections.actionRecruitmentFragmentToApplyPlubbingFragment()
        findNavController().navigate(action)
    }



}