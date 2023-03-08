package com.plub.presentation.ui.main.home.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.MyPageGatheringType
import com.plub.domain.model.vo.myPage.MyPageGatheringDetailVo
import com.plub.domain.model.vo.myPage.MyPageGatheringVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageBinding
import com.plub.presentation.ui.PageState
import com.plub.presentation.ui.main.home.profile.adapter.MyPageParentGatheringAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, PageState.Default, MyPageViewModel>(
        FragmentMyPageBinding::inflate
    ) {

    private val gatheringAdapter : MyPageParentGatheringAdapter by lazy {
        MyPageParentGatheringAdapter(object : MyPageParentGatheringAdapter.MyPageDelegate{
            override fun onClickGathering(gatheringType: MyPageGatheringType) {
                viewModel.goToDetail(gatheringType)
            }
        })
    }

    override val viewModel: MyPageViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            gatheringAdapter.submitList(arrayListOf(
                MyPageGatheringVo(gatheringList = arrayListOf(MyPageGatheringDetailVo(
                    title = "테스트용 1번",
                    goal = "테스트용 1번",
                    gatheringType = MyPageGatheringType.RECRUITING
                )), gatheringType = MyPageGatheringType.RECRUITING),
                MyPageGatheringVo(gatheringList = arrayListOf(MyPageGatheringDetailVo(
                    title = "테스트용 2번",
                    goal = "테스트용 2번",
                    gatheringType = MyPageGatheringType.WAITING
                )), gatheringType = MyPageGatheringType.WAITING),
                MyPageGatheringVo(gatheringList = arrayListOf(MyPageGatheringDetailVo(
                    title = "테스트용 3번",
                    goal = "테스트용 3번",
                    gatheringType = MyPageGatheringType.ACTIVE
                )), gatheringType = MyPageGatheringType.ACTIVE),
                MyPageGatheringVo(gatheringList = arrayListOf(MyPageGatheringDetailVo(
                    title = "테스트용 4번",
                    goal = "테스트용 4번",
                    gatheringType = MyPageGatheringType.END
                )), gatheringType = MyPageGatheringType.END)))
            initRecycler()
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
            is MyPageEvent.GoToMyApplication -> {}
            is MyPageEvent.GoToOtherApplication -> {findNavController().navigate(MyPageFragmentDirections.myPageToRecruitingGathering())}
        }
    }
}