package com.plub.presentation.ui.main.profile.waiting

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.ApplyModifyApplicationType
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentMyPageWaitingGatheringBinding
import com.plub.presentation.ui.common.dialog.CommonDialog
import com.plub.presentation.ui.main.profile.adapter.MyPageDetailPageAdapter
import com.plub.presentation.ui.main.profile.MyPageApplicantsGatheringState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WaitingGatheringFragment :
    BaseTestFragment<FragmentMyPageWaitingGatheringBinding, MyPageApplicantsGatheringState, WaitingGatheringViewModel>(
        FragmentMyPageWaitingGatheringBinding::inflate
    ) {

    @Inject
    lateinit var commonDialog: CommonDialog

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
                viewModel.uiState.detailList.collect {
                    myPageDetailPageAdapter.submitList(it)
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
        commonDialog
            .setTitle(R.string.my_page_again_cancel)
            .setPositiveButton(R.string.word_yes) {
                viewModel.deleteMyApplication(myPageWaitingGatheringFragmentArgs.plubbingId)
                commonDialog.dismiss()
            }
            .setNegativeButton(R.string.word_no) {
                commonDialog.dismiss()
            }
            .show()
    }
}