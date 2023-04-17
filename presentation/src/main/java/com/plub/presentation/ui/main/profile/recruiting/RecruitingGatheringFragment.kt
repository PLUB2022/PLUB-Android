package com.plub.presentation.ui.main.profile.recruiting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentMyPageRecruitingGatheringBinding
import com.plub.presentation.ui.common.dialog.CommonDialog
import com.plub.presentation.ui.main.profile.MyPageApplicantsGatheringState
import com.plub.presentation.ui.main.profile.adapter.MyPageDetailPageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecruitingGatheringFragment :
    BaseTestFragment<FragmentMyPageRecruitingGatheringBinding, MyPageApplicantsGatheringState, RecruitingGatheringViewModel>(
        FragmentMyPageRecruitingGatheringBinding::inflate
    ) {

    @Inject
    lateinit var commonDialog: CommonDialog

    private val recruitingGatheringFragmentArgs: RecruitingGatheringFragmentArgs by navArgs()
    private val myPageDetailPageAdapter : MyPageDetailPageAdapter by lazy {
        MyPageDetailPageAdapter(object : MyPageDetailPageAdapter.ApplicantsDelegate{
            override fun onClickApproveButton(accountId: Int) {
                viewModel.showApproveDialog(accountId)
            }

            override fun onClickRejectButton(accountId: Int) {
                viewModel.showRefuseDialog(accountId)
            }

            override fun onClickCancelButton() {

            }

            override fun onClickModifyButton() {

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
                viewModel.uiState.detailList.collect {
                    myPageDetailPageAdapter.submitList(it)
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
            is MyPageRecruitingGatheringEvent.ShowApproveDialog -> showApproveDialog(event.accountId)
            is MyPageRecruitingGatheringEvent.ShowRefuseDialog -> showRefuseDialog(event.accountId)
            is MyPageRecruitingGatheringEvent.GoToBack -> findNavController().popBackStack()
        }
    }

    private fun goToRecruit(){
        val action = RecruitingGatheringFragmentDirections.myPageRecruitingToRecruitment(recruitingGatheringFragmentArgs.plubbingId)
        findNavController().navigate(action)
    }

    private fun showApproveDialog(accountId : Int){
        commonDialog
            .setTitle(R.string.my_page_again_approve)
            .setPositiveButton(R.string.my_page_approve) {
                viewModel.approve(accountId)
                commonDialog.dismiss()
            }
            .setNegativeButton(R.string.word_cancel) {
                commonDialog.dismiss()
            }
            .show()
    }

    private fun showRefuseDialog(accountId : Int){
        commonDialog
            .setTitle(R.string.my_page_again_refuse)
            .setPositiveButton(R.string.my_page_reject) {
                viewModel.reject(accountId)
                commonDialog.dismiss()
            }
            .setNegativeButton(R.string.word_cancel) {
                commonDialog.dismiss()
            }
            .show()
    }
}