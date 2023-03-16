package com.plub.presentation.ui.main.profile.recruiting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageRecruitingGatheringBinding
import com.plub.presentation.ui.main.profile.adapter.MyPageDetailPageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecruitingGatheringFragment :
    BaseFragment<FragmentMyPageRecruitingGatheringBinding, MyPageApplicantsGatheringState, RecruitingGatheringViewModel>(
        FragmentMyPageRecruitingGatheringBinding::inflate
    ) {

    private val recruitingGatheringFragmentArgs: RecruitingGatheringFragmentArgs by navArgs()
    private val myPageDetailPageAdapter : MyPageDetailPageAdapter by lazy {
        MyPageDetailPageAdapter(object : MyPageDetailPageAdapter.ApplicantsDelegate{
            override fun onClickApproveButton(accountId: Int) {
                viewModel.approve(accountId)
            }

            override fun onClickRejectButton(accountId: Int) {
                viewModel.reject(accountId)
            }
        })
    }

    override val viewModel: RecruitingGatheringViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerViewMyPageContent.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = myPageDetailPageAdapter
            }
        }

        viewModel.getPageDetail(recruitingGatheringFragmentArgs.plubbingId)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    myPageDetailPageAdapter.submitList(it.detailList)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as MyPageRecruitingGatheringEvent)
                }
            }
        }
    }

    private fun inspectEvent(event : MyPageRecruitingGatheringEvent){
        when(event){
            is MyPageRecruitingGatheringEvent.GoToRecruit -> goToRecruit()
        }
    }

    private fun goToRecruit(){
        val action = RecruitingGatheringFragmentDirections.myPageRecruitingToRecruitment(recruitingGatheringFragmentArgs.plubbingId)
        findNavController().navigate(action)
    }
}