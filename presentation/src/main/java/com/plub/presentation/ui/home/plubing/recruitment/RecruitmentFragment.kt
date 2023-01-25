package com.plub.presentation.ui.home.plubing.recruitment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.plub.presentation.state.SampleHomeState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentDetailRecruitmentPlubingBinding
import com.plub.presentation.ui.home.plubing.categoryChoice.CategoryChoiceFragmentArgs
import com.plub.presentation.ui.home.plubing.main.MainFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecruitmentFragment : BaseFragment<FragmentDetailRecruitmentPlubingBinding, SampleHomeState, RecruitmentViewModel>(
    FragmentDetailRecruitmentPlubingBinding::inflate
)  {
    private val plubbingIdForMain: MainFragmentArgs by navArgs()
    private val plubbingIdForCategoryChoice: CategoryChoiceFragmentArgs by navArgs()
    override val viewModel: RecruitmentViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            //TODO 할 일
            buttonJoin.setOnClickListener {
                goToApplyPlubbingFragment(returnFragmentArgs())
            }
        }
    }

    override fun initStates() {
        //TODO("Not yet implemented")
        repeatOnStarted(viewLifecycleOwner) {
            launch {
//                viewModel.goToApplyFragment.collect{
//                    goToApplyPlubbingFragment()
//                }
            }

        }
    }

    fun goToApplyPlubbingFragment(plubbingId : String){
        val action = RecruitmentFragmentDirections.actionRecruitmentFragmentToApplyPlubbingFragment(plubbingId)
        findNavController().navigate(action)
    }

    fun returnFragmentArgs() : String{
        if(plubbingIdForMain.plubbingId.equals("0")){
            return plubbingIdForCategoryChoice.plubbingId
        }
        else
            return plubbingIdForMain.plubbingId
    }



}