package com.plub.presentation.ui.main.home.recruitment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.plub.domain.model.enums.ApplyModifyApplicationType
import com.plub.domain.model.sealed.ReportType
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailJoinedAccountsVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentDetailRecruitmentPlubingBinding
import com.plub.presentation.ui.common.dialog.CommonDialog
import com.plub.presentation.ui.main.home.recruitment.adapter.DetailRecruitCategoryAdapter
import com.plub.presentation.ui.main.home.recruitment.adapter.DetailRecruitProfileAdapter
import com.plub.presentation.ui.main.home.recruitment.bottomsheet.ProfileBottomSheetFragment
import com.plub.presentation.ui.main.home.recruitment.dialog.RecruitApplySuccessDialogFragment
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecruitmentFragment :
    BaseTestFragment<FragmentDetailRecruitmentPlubingBinding, RecruitmentPageState, RecruitmentViewModel>(
        FragmentDetailRecruitmentPlubingBinding::inflate
    ) {

    companion object{
        private const val PROFILE_WIDTH = 42
    }
    @Inject
    lateinit var commonDialog: CommonDialog

    private val recruitmentFragmentArgs : RecruitmentFragmentArgs by navArgs()
    private val detailRecruitProfileAdapter: DetailRecruitProfileAdapter by lazy {
        DetailRecruitProfileAdapter(object : DetailRecruitProfileAdapter.DetailProfileDelegate {
            override fun onProfileClick(accountId: Int, nickname : String) {
                viewModel.goToProfile(accountId, nickname)
            }

            override fun onSeeMoreProfileClick() {
                viewModel.openBottomSheet()
            }

        })
    }
    private val detailRecruitCategoryAdapter : DetailRecruitCategoryAdapter by lazy {
        DetailRecruitCategoryAdapter()
    }
    override val viewModel: RecruitmentViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
            initRecycler()
        }
        viewModel.fetchRecruitmentDetail(recruitmentFragmentArgs.plubbingId)
    }

    private fun initRecycler(){
        binding.apply {
            recyclerViewPlubbingHobby.apply {
                layoutManager = FlexboxLayoutManager(context)
                adapter = detailRecruitCategoryAdapter
            }
            recyclerViewPlubbingPeopleProfile.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = detailRecruitProfileAdapter
            }
        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.joinedAccounts.collect {
                    initProfileAdapter(it)
                }
            }
            launch {
                viewModel.uiState.categories.collect{
                    detailRecruitCategoryAdapter.submitList(it)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as RecruitEvent)
                }
            }
        }
    }

    private fun initProfileAdapter(list: List<RecruitDetailJoinedAccountsVo>) {
        binding.apply {
            val maxProfile = recyclerViewPlubbingPeopleProfile.width / PROFILE_WIDTH.px
            constraintLayoutTop.bringToFront()
            detailRecruitProfileAdapter.setMaxProfile(maxProfile)
            detailRecruitProfileAdapter.submitList(list)
        }
    }

    private fun inspectEventFlow(event : RecruitEvent){
        when(event){
            is RecruitEvent.GoToApplyPlubbingFragment-> {
                goToApplyPlubbingFragment(recruitmentFragmentArgs.plubbingId)
            }
            is RecruitEvent.GoToProfileFragment ->{
                goToProfile(event.accountId, event.nickname)
            }
            is RecruitEvent.GoToBack -> {
                findNavController().popBackStack()
            }
            is RecruitEvent.OpenBottomSheet -> {
                openProfileBottomSheet(event.joinedAccountsList)
            }
            is RecruitEvent.GoToReport -> {
                goToReport()
            }
            is RecruitEvent.CancelApply -> {
                showCancelDialog()
            }
            is RecruitEvent.ShowDialog -> {
                showSuccessDialog()
            }
            is RecruitEvent.GoToEditFragment -> {
                goToModifyGathering()
            }
            is RecruitEvent.GoToSeeApplicants -> {

            }
        }
    }

    private fun goToApplyPlubbingFragment(plubbingId: Int) {
        val action = RecruitmentFragmentDirections.actionRecruitmentToApplyPlubbing(
            plubbingId = plubbingId,
            pageType = ApplyModifyApplicationType.APPLY
            )
        findNavController().navigate(action)
    }

    private fun goToProfile(accountId: Int, nickname : String) {
        val action = RecruitmentFragmentDirections.actionRecruitmentToOtherProfile(
            plubbingId = recruitmentFragmentArgs.plubbingId,
            accountId = accountId,
            nickname = nickname
        )
        findNavController().navigate(action)
    }

    private fun openProfileBottomSheet(joinedAccountList : List<RecruitDetailJoinedAccountsVo>){
        val bottomSheet = ProfileBottomSheetFragment(joinedAccountList)
        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }

    private fun goToReport(){
        val action = RecruitmentFragmentDirections.actionRecruitmentToReport(
            ReportType.RecruitReport(plubbingId = recruitmentFragmentArgs.plubbingId)
        )
        findNavController().navigate(action)
    }

    private fun showCancelDialog(){
        commonDialog
            .setTitle(R.string.my_page_again_cancel)
            .setGoneDescription()
            .setPositiveButton(R.string.word_yes){
                viewModel.cancelApply()
                commonDialog.dismiss()
            }
            .setNegativeButton(R.string.word_no){
                commonDialog.dismiss()
            }
            .show()
    }

    private fun showSuccessDialog(){
        RecruitApplySuccessDialogFragment(object : RecruitApplySuccessDialogFragment.Delegate{
            override fun closeButtonClick() {
                initView()
            }
        }).show(childFragmentManager, "")
    }

    private fun goToModifyGathering(){
        val action = RecruitmentFragmentDirections.actionRecruitmentToModifyGathering()
        findNavController().navigate(action)
    }
}