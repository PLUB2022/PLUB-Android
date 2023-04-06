package com.plub.presentation.ui.main.home.recruitment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.plub.domain.model.enums.ApplyModifyApplicationType
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailJoinedAccountsVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentDetailRecruitmentPlubingBinding
import com.plub.presentation.ui.main.home.recruitment.adapter.DetailRecruitCategoryAdapter
import com.plub.presentation.ui.main.home.recruitment.adapter.DetailRecruitProfileAdapter
import com.plub.presentation.ui.main.home.recruitment.bottomsheet.ProfileBottomSheetFragment
import com.plub.presentation.ui.main.profile.bottomsheet.BottomSheetProfileFragment
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecruitmentFragment :
    BaseFragment<FragmentDetailRecruitmentPlubingBinding, DetailRecruitPageState, RecruitmentViewModel>(
        FragmentDetailRecruitmentPlubingBinding::inflate
    ) {

    companion object{
        private const val PROFILE_WIDTH = 42
    }
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
            viewModel.fetchRecruitmentDetail(recruitmentFragmentArgs.plubbingId)
        }
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
                viewModel.uiState.collect {
                    initDetailPage(it)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as RecruitEvent)
                }
            }
        }
    }

    private fun initDetailPage(data: DetailRecruitPageState) {
        binding.apply {
            val maxProfile = recyclerViewPlubbingPeopleProfile.width / PROFILE_WIDTH.px
            constraintLayoutTop.bringToFront()
            GlideUtil.loadImage(root.context, data.plubbingMainImage, imageViewPlubbingImage)
            imageViewPlubbingImage.clipToOutline = true
            detailRecruitCategoryAdapter.submitList(data.categories)
            detailRecruitProfileAdapter.setMaxProfile(maxProfile)
            detailRecruitProfileAdapter.submitList(data.joinedAccounts)
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
        val bottomSheetProfileFragment = BottomSheetProfileFragment.newInstance(
            accountId = accountId,
            nickName = nickname,
            plubbingId = recruitmentFragmentArgs.plubbingId
        )
        bottomSheetProfileFragment.show(
            parentFragmentManager,
            bottomSheetProfileFragment.tag
        )
    }

    private fun openProfileBottomSheet(joinedAccountList : List<RecruitDetailJoinedAccountsVo>){
        val bottomSheet = ProfileBottomSheetFragment(joinedAccountList)
        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }

    private fun goToReport(){
        //TODO 신고화면 이동
    }
}