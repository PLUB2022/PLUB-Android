package com.plub.presentation.ui.home.plubing.recruitment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.plub.domain.model.vo.home.recruitdetailvo.RecruitDetailResponseVo
import com.plub.presentation.R
import com.plub.presentation.state.SampleHomeState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentDetailRecruitmentPlubingBinding
import com.plub.presentation.ui.home.plubing.categoryChoice.CategoryChoiceFragmentArgs
import com.plub.presentation.ui.home.plubing.main.MainFragmentArgs
import com.plub.presentation.util.GlideUtil
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
            viewModel.fetchRecruitmentDetail(returnFragmentArgs().toInt())
        }
    }

    override fun initStates() {
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.recruitMentDetailData.collect{
                    initDetailPage(it)
                }
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

    fun initDetailPage(data : RecruitDetailResponseVo){
        binding.apply {
            if(data.isBookmarked){
                imageBtnBookmark.setImageResource(R.drawable.ic_bookmark_checked)
            }
            textViewPlubbingName.text = data.plubbingName
            textViewPlubbingTitle.text = data.recruitTitle
            //textViewLocation.text = data.location
            textViewPlubbingGoal.text = "“${data.plubbingGoal}”"
            //textViewPeople.text = "모집 인원 ${data.maxAccountNum}명"
            textViewDate.text = "매주~~"
            imageViewPlubbingImage
            GlideUtil.loadImage(root.context, data.plubbingMainImage, imageViewPlubbingImage)
            textViewPlubbingDetailIntro.text = "[ ${data.plubbingName} ] 모임은요...!"
            textViewPlubbingDetail.text = data.recruitIntroduce
            //recycler_view_plubbing_hobby
            //recycler_view_plubbing_people_profile
        }
    }
}