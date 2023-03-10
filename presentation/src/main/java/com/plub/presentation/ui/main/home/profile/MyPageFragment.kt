package com.plub.presentation.ui.main.home.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageBinding
import com.plub.presentation.ui.main.home.profile.adapter.MyPageParentGatheringAdapter
import com.plub.presentation.util.GlideUtil
import com.plub.presentation.util.PlubUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageState, MyPageViewModel>(
        FragmentMyPageBinding::inflate
    ) {

    companion object{
        const val LEAST_LINE = 1
    }

    private val gatheringAdapter : MyPageParentGatheringAdapter by lazy {
        MyPageParentGatheringAdapter(object : MyPageParentGatheringAdapter.MyPageDelegate{
            override fun onClickCardExpand(gatheringType: MyPageGatheringStateType) {
                viewModel.onClickExpand(gatheringType)
            }

            override fun onClickGathering(gatheringType: MyPageGatheringStateType) {
                viewModel.goToDetail(gatheringType)
            }
        })
    }

    override val viewModel: MyPageViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
            viewModel.getMyPageData()
            // TODO 주석 제거 및 아래 하드코딩 제거
            // setMyInfo()
            textViewProfileName.text = "조경석"
            textViewProfileExplain.text = "국회는 법률에 저촉되지 아니하는 범위안에서 의사와 내부규율에 관한 규칙을 제정할 수 있다. 국회는 법률에 저촉되지 아니하는 범위안에서 의사와 내부규율에 관한 규칙을 제정할 수 있다. 국회는 법률에 저촉되지 아니하는 범위안에서 의사와 내부규율에 관한 규칙을 제정할 수 있다."
            initRecycler()
        }
    }

    private fun setMyInfo(){
        binding.apply {
            textViewProfileName.text = PlubUser.info.nickname
            textViewProfileExplain.text = PlubUser.info.introduce
            GlideUtil.loadImage(root.context, PlubUser.info.profileImage, imageViewProfile)
            imageViewProfile.clipToOutline = true
        }
    }

    private fun initRecycler(){
        binding.apply {
            recyclerViewMyGathering.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = gatheringAdapter
            }
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    gatheringAdapter.submitList(it.myPageGatheringList)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEvent(it as MyPageEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: MyPageEvent) {
        when (event) {
            is MyPageEvent.GoToMyApplication -> {findNavController().navigate(MyPageFragmentDirections.myPageToWaitingGathering())}
            is MyPageEvent.GoToOtherApplication -> {findNavController().navigate(MyPageFragmentDirections.myPageToRecruitingGathering())}
            is MyPageEvent.ReadMore -> { openText(event.isExpandText) }
            is MyPageEvent.GoToSetting -> {findNavController().navigate(MyPageFragmentDirections.myPageToSetting())}
        }
    }

    private fun openText(isExpandText : Boolean){
        binding.apply {
            if(isExpandText){
                textViewReadMore.text = getString(R.string.my_page_close)
                textViewProfileExplain.maxLines = Int.MAX_VALUE
            }
            else{
                textViewReadMore.text = getString(R.string.my_page_read_more)
                textViewProfileExplain.maxLines = LEAST_LINE
            }
        }
    }
}