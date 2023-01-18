package com.plub.presentation.ui.home.plubing.recruitment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.presentation.R
import com.plub.presentation.state.SampleHomeState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentDetailRecruitmentPlubingBinding
import com.plub.presentation.state.PageState
import com.plub.presentation.ui.common.GridSpaceDecoration
import com.plub.presentation.ui.home.adapter.DetailRecruitCategoryAdapter
import com.plub.presentation.ui.home.adapter.DetailRecruitProfileAdapter
import com.plub.presentation.ui.home.plubing.categoryChoice.CategoryChoiceFragmentArgs
import com.plub.presentation.ui.home.plubing.main.MainFragmentArgs
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecruitmentFragment :
    BaseFragment<FragmentDetailRecruitmentPlubingBinding, PageState.Default, RecruitmentViewModel>(
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
    override val viewModel: RecruitmentViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            //TODO 할 일
            buttonJoin.setOnClickListener {
                goToApplyPlubbingFragment(returnFragmentArgs())
            }
            viewModel.fetchRecruitmentDetail(returnFragmentArgs().toInt())
        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.recruitMentDetailData.collect {
                    initDetailPage(it)
                }
            }
        }
    }

    fun goToApplyPlubbingFragment(plubbingId: String) {
        val action = RecruitmentFragmentDirections.actionRecruitmentFragmentToApplyPlubbingFragment(
            plubbingId
        )
        findNavController().navigate(action)
    }

    fun returnFragmentArgs(): String {
        if (plubbingIdForMain.plubbingId.equals("0")) {
            return plubbingIdForCategoryChoice.plubbingId
        } else
            return plubbingIdForMain.plubbingId
    }

    fun goToProfile(accountId: Int) {

    }

    fun initDetailPage(data: RecruitDetailResponseVo) {
        binding.apply {
            if (data.isBookmarked) {
                imageBtnBookmark.setImageResource(R.drawable.ic_bookmark_checked)
            }
            textViewPlubbingName.text = data.plubbingName
            textViewPlubbingTitle.text = data.recruitTitle
            textViewLocation.text = data.placeName
            textViewPlubbingGoal.text = "“${data.plubbingGoal}”"
            textViewPeople.text = "모집 인원 ${data.curAccountNum + data.remainAccountNum}명"
            textViewDate.text = "${data.plubbingDays}"
            //GlideUtil.loadImage(root.context, data.plubbingMainImage, imageViewPlubbingImage)
            textViewPlubbingDetailIntro.text = "[ ${data.plubbingName} ] 모임은요...!"
            textViewPlubbingDetail.text = data.recruitIntroduce
            val detailRecruitCategoryAdapter = DetailRecruitCategoryAdapter()
            detailRecruitCategoryAdapter.submitList(data.categories)
            recyclerViewPlubbingHobby.apply {
                layoutManager = GridLayoutManager(context, 4)
                addItemDecoration(GridSpaceDecoration(4, 8.px, false))
                adapter = detailRecruitCategoryAdapter
            }
            detailRecruitProfileAdapter.submitList(data.joinedAccounts)
            recyclerViewPlubbingPeopleProfile.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = detailRecruitProfileAdapter
            }
        }
    }
}
