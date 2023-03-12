package com.plub.presentation.ui.main.home.profile.active

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.PlubingBoardWriteType
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageActiveGatheringBinding
import com.plub.presentation.ui.common.decoration.VerticalSpaceDecoration
import com.plub.presentation.ui.main.home.profile.active.adapter.ActiveGatheringParentAdapter
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActiveGatheringFragment :
    BaseFragment<FragmentMyPageActiveGatheringBinding, ActiveGatheringPageState, ActiveGatheringViewModel>(
        FragmentMyPageActiveGatheringBinding::inflate
    ) {

    companion object{
        const val VERTICAL_SPACE = 24
    }

    private val activeGatheringFragmentArgs : ActiveGatheringFragmentArgs by navArgs()

    private val activeGatheringParentAdapter: ActiveGatheringParentAdapter by lazy {
        ActiveGatheringParentAdapter(object : ActiveGatheringParentAdapter.ActiveGatheringDelegate{
            override fun onClickBoard(feedId: Int) {
                viewModel.onClickBoard(feedId)
            }

            override fun onClickNew() {
                findNavController().navigate(ActiveGatheringFragmentDirections.goNew(writeType = PlubingBoardWriteType.CREATE))
            }

            override fun onClickSeeAll() {
                findNavController().navigate(ActiveGatheringFragmentDirections.goAll(activeGatheringFragmentArgs.plubbingId))
            }
        })
    }

    override val viewModel: ActiveGatheringViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel

            buttonGoGathering.setOnClickListener {
                findNavController().navigate(ActiveGatheringFragmentDirections.actionMyPageRecruitToRecruit(activeGatheringFragmentArgs.plubbingId))
            }

            recyclerViewMyPageContent.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(VerticalSpaceDecoration(VERTICAL_SPACE.px))
                adapter = activeGatheringParentAdapter
            }

        }
        viewModel.setPlubIdAndStateType(activeGatheringFragmentArgs.plubbingId, activeGatheringFragmentArgs.stateType)
        viewModel.setView()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    activeGatheringParentAdapter.submitList(it.detailList)
                }
            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as ActiveGatheringEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: ActiveGatheringEvent) {
        when (event) {
            is ActiveGatheringEvent.GoToDetailBoard -> goToDetailBoard(event.feedId)
        }
    }

    private fun goToDetailBoard(feedId: Int) {
        val action = ActiveGatheringFragmentDirections.actionMyPageActiveDetailToPlubingBoardDetail(feedId)
        findNavController().navigate(action)
    }
}