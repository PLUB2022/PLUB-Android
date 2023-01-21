package com.plub.presentation.ui.home.plubing.recruitment.hostrecruitment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentHostDetailRecruitmentPlubbingBinding
import com.plub.presentation.state.PageState
import com.plub.presentation.ui.common.GridSpaceDecoration
import com.plub.presentation.ui.home.plubing.categoryChoice.CategoryChoiceFragmentArgs
import com.plub.presentation.ui.home.plubing.main.MainFragmentArgs
import com.plub.presentation.ui.home.plubing.recruitment.adapter.DetailRecruitCategoryAdapter
import com.plub.presentation.ui.home.plubing.recruitment.adapter.DetailRecruitProfileAdapter
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HostRecruitmentFragment :
    BaseFragment<FragmentHostDetailRecruitmentPlubbingBinding, PageState.Default, HostRecruitmentViewModel>(
        FragmentHostDetailRecruitmentPlubbingBinding::inflate
    ) {
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
    override val viewModel: HostRecruitmentViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            //TODO 할 일
            buttonEnd.setOnClickListener {
                //viewModel.endRecruit(plubbingId)
            }

            buttonSeeApplicants.setOnClickListener {
                //viewModel.seeApplicants(plubbingId)
            }
            //viewModel.fetchRecruitmentDetail(plubbingId = )
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

    fun goToProfile(accountId: Int) {

    }

    fun initDetailPage(data: RecruitDetailResponseVo) {
        binding.apply {
            constraintLayoutTop.bringToFront()
            textViewPlubbingName.text = data.plubbingName
            textViewPlubbingTitle.text = data.recruitTitle
            textViewLocation.text = data.placeName
            textViewPlubbingGoal.text = "“${data.plubbingGoal}”"
            textViewPeople.text = "모집 인원 ${data.curAccountNum + data.remainAccountNum}명"
            textViewDate.text = "${data.plubbingDays}"
            //GlideUtil.loadImage(root.context, data.plubbingMainImage, imageViewPlubbingImage)
            textViewPlubbingDetailIntro.text = "[ ${data.plubbingName} ] 모임은요...!"
            textViewPlubbingDetail.text = data.recruitIntroduce

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
