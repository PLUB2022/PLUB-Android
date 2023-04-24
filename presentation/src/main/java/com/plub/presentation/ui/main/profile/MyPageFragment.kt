package com.plub.presentation.ui.main.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.MyPageGatheringMyType
import com.plub.domain.model.enums.MyPageGatheringStateType
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentMyPageBinding
import com.plub.presentation.ui.main.profile.adapter.MyPageParentGatheringAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyPageFragment :
    BaseTestFragment<FragmentMyPageBinding, MyPageState, MyPageViewModel>(
        FragmentMyPageBinding::inflate
    ) {

    companion object{
        const val LEAST_LINE = 1
    }

    private val gatheringAdapter : MyPageParentGatheringAdapter by lazy {
        MyPageParentGatheringAdapter(object : MyPageParentGatheringAdapter.MyPageDelegate{
            override fun onClickGathering(
                gatheringParentType: MyPageGatheringStateType,
                gatheringType: MyPageGatheringMyType,
                plubbingId : Int
            ) {
                viewModel.goToDetail(gatheringParentType, plubbingId, gatheringType)
            }

            override fun onClickEdit() {
                viewModel.goToEdit()
            }

            override fun onClickGoToHome() {
                viewModel.goToHome()
            }

        })
    }

    override val viewModel: MyPageViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
        viewModel.getMyPageData()
        viewModel.setMyInfo()
        initRecycler()
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
                viewModel.uiState.myPageVo.collect {
                    gatheringAdapter.submitList(it)
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
            is MyPageEvent.GoToMyApplication -> {findNavController().navigate(MyPageFragmentDirections.myPageToWaitingGathering(event.plubbingId))}
            is MyPageEvent.GoToOtherApplication -> {findNavController().navigate(MyPageFragmentDirections.myPageToRecruitingGathering(event.plubbingId))}
            is MyPageEvent.GoToActiveGathering -> {findNavController().navigate(MyPageFragmentDirections.myPageToActiveGathering(event.gatheringType, event.plubbingId))}
            is MyPageEvent.GoToHome -> {findNavController().navigate(MyPageFragmentDirections.myPageToHome())}
            is MyPageEvent.GoToEdit -> {findNavController().navigate(MyPageFragmentDirections.myPageToEdit())}
            is MyPageEvent.GoToSetting -> {findNavController().navigate(MyPageFragmentDirections.actionMyPageToSetting())}
        }
    }

}