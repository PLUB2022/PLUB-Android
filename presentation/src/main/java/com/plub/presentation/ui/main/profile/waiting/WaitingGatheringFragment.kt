package com.plub.presentation.ui.main.profile.waiting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.ApplyModifyApplicationType
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageWaitingGatheringBinding
import com.plub.presentation.ui.main.profile.adapter.MyPageDetailPageAdapter
import com.plub.presentation.ui.main.profile.MyPageApplicantsGatheringState
import com.plub.presentation.ui.main.profile.setting.dialog.MyPageSettingConfirmDialogFragment
import com.plub.presentation.ui.main.profile.waiting.dialog.MyPageWaitingAgainCancelDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WaitingGatheringFragment :
    BaseFragment<FragmentMyPageWaitingGatheringBinding, MyPageApplicantsGatheringState, WaitingGatheringViewModel>(
        FragmentMyPageWaitingGatheringBinding::inflate
    ) {

    private val myPageWaitingGatheringFragmentArgs: WaitingGatheringFragmentArgs by navArgs()

    private val myPageDetailPageAdapter: MyPageDetailPageAdapter by lazy {
        MyPageDetailPageAdapter(object : MyPageDetailPageAdapter.ApplicantsDelegate {
            override fun onClickApproveButton(accountId: Int) {
            }

            override fun onClickRejectButton(accountId: Int) {

            }

            override fun onClickCancelButton() {
                viewModel.showCancelDialog()
            }

            override fun onClickModifyButton() {
                viewModel.goToModifyApplication()
            }
        })
    }

    override val viewModel: WaitingGatheringViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
            recyclerViewMyPageContent.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = myPageDetailPageAdapter
            }
        }

        viewModel.getPageDetail(myPageWaitingGatheringFragmentArgs.plubbingId)
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
                viewModel.eventFlow.collect {
                    inspectEvent(it as WaitingGatheringEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: WaitingGatheringEvent) {
        when (event) {
            is WaitingGatheringEvent.GoToBack -> findNavController().popBackStack()
            is WaitingGatheringEvent.GoToModifyApplication -> goToApply()
            is WaitingGatheringEvent.ShowCancelDialog -> showCancelDialog()
        }
    }

    private fun goToApply() {
        val action = WaitingGatheringFragmentDirections.myPageWaitingToApply(
            plubbingId = myPageWaitingGatheringFragmentArgs.plubbingId,
            pageType = ApplyModifyApplicationType.MODIFY
        )
        findNavController().navigate(action)
    }

    private fun showCancelDialog() {
        MyPageWaitingAgainCancelDialogFragment(object :
            MyPageWaitingAgainCancelDialogFragment.Delegate {
            override fun onYesButtonClick() {
                viewModel.deleteMyApplication(myPageWaitingGatheringFragmentArgs.plubbingId)
            }

        }).show(childFragmentManager, "")
    }
}