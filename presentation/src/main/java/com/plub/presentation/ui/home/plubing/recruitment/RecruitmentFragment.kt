package com.plub.presentation.ui.home.plubing.recruitment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentDetailRecruitmentPlubingBinding
import com.plub.presentation.event.RecruitEvent
import com.plub.presentation.state.DetailRecruitPageState
import com.plub.presentation.ui.common.GridSpaceDecoration
import com.plub.presentation.ui.home.adapter.DetailRecruitCategoryAdapter
import com.plub.presentation.ui.home.adapter.DetailRecruitProfileAdapter
import com.plub.presentation.ui.home.plubing.categoryChoice.CategoryChoiceFragmentArgs
import com.plub.presentation.ui.home.plubing.main.MainFragmentArgs
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecruitmentFragment :
    BaseFragment<FragmentDetailRecruitmentPlubingBinding, DetailRecruitPageState, RecruitmentViewModel>(
        FragmentDetailRecruitmentPlubingBinding::inflate
    ) {
    private val plubbingIdForMain: MainFragmentArgs by navArgs()
    private val plubbingIdForCategoryChoice: CategoryChoiceFragmentArgs by navArgs()
    private val detailRecruitProfileAdapter: DetailRecruitProfileAdapter by lazy {
        DetailRecruitProfileAdapter(object : DetailRecruitProfileAdapter.DetailProfileDegelate {
            override fun onProfileClick(accountId: Int) {
                goToProfile(accountId)
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
            //TODO 할 일
            buttonJoin.setOnClickListener {
                viewModel
            }
            viewModel.fetchRecruitmentDetail(returnFragmentArgs().toInt())
        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    initDetailPage(it.recruitDetailData)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as RecruitEvent)
                }
            }
        }
    }

    private fun goToApplyPlubbingFragment(plubbingId: String) {
        val action = RecruitmentFragmentDirections.actionRecruitmentFragmentToApplyPlubbingFragment(
            plubbingId
        )
        findNavController().navigate(action)
    }

    private fun returnFragmentArgs(): String {
        if (plubbingIdForMain.plubbingId.equals("0")) {
            return plubbingIdForCategoryChoice.plubbingId
        } else
            return plubbingIdForMain.plubbingId
    }

    private fun inspectEventFlow(event : RecruitEvent){
        when(event){
            RecruitEvent.GoToApplyPlubbingFragment-> goToApplyPlubbingFragment(returnFragmentArgs())
            RecruitEvent.GoToProfileFragment ->{}
        }
    }

    private fun goToProfile(accountId: Int) {

    }

    private fun initDetailPage(data: RecruitDetailResponseVo) {
        var bookmarkFlag = data.isBookmarked
        binding.apply {
            constraintLayoutTop.bringToFront()
            viewModel.updateState(data.isApplied)
            if(data.isApplied){
                buttonJoin.text = getString(R.string.detail_recruitment_already_apply)
            }
            if (data.isBookmarked) {
                imageBtnBookmark.setImageResource(R.drawable.ic_bookmark_checked)
            }
            textViewPlubbingName.text = data.plubbingName
            textViewPlubbingTitle.text = data.recruitTitle
            textViewLocation.text = data.placeName
            textViewPlubbingGoal.text = "“${data.plubbingGoal}”"

            textViewPeople.text = getString(R.string.detail_recruitment_people,(data.curAccountNum + data.remainAccountNum).toString())
            textViewDate.text = "${data.plubbingDays}"
            //GlideUtil.loadImage(root.context, data.plubbingMainImage, imageViewPlubbingImage)
            textViewPlubbingDetailIntro.text = getString(R.string.detail_recruit_about_my_gathering,data.recruitIntroduce)
            textViewPlubbingDetail.text = data.recruitIntroduce

            detailRecruitCategoryAdapter.submitList(data.categories)
            recyclerViewPlubbingHobby.apply {
                layoutManager = GridLayoutManager(context, 4)
                addItemDecoration(GridSpaceDecoration(4, 8.px, 8.px, false))
                adapter = detailRecruitCategoryAdapter
            }

            detailRecruitProfileAdapter.submitList(data.joinedAccounts)
            recyclerViewPlubbingPeopleProfile.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = detailRecruitProfileAdapter
            }

            imageBtnBookmark.setOnClickListener{
                if(bookmarkFlag){
                    imageBtnBookmark.setImageResource(R.drawable.ic_bookmark)
                }
                else{
                    imageBtnBookmark.setImageResource(R.drawable.ic_bookmark_checked)
                }
                bookmarkFlag = !bookmarkFlag
                viewModel.clickBookmark(returnFragmentArgs().toInt())
            }
        }
    }
}