package com.plub.presentation.ui.main.home.profile.active

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.board.PlubingBoardVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentMyPageActiveGatheringBinding
import com.plub.presentation.ui.main.home.profile.active.adapter.ActiveGatheringParentAdapter
import com.plub.presentation.ui.main.plubing.board.adapter.PlubingBoardAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActiveGatheringFragment :
    BaseFragment<FragmentMyPageActiveGatheringBinding, ActiveGatheringPageState, ActiveGatheringViewModel>(
        FragmentMyPageActiveGatheringBinding::inflate
    ) {

    private val activeGatheringFragmentArgs : ActiveGatheringFragmentArgs by navArgs()

    private val boardListAdapter: PlubingBoardAdapter by lazy {
        PlubingBoardAdapter(object : PlubingBoardAdapter.Delegate {
            override fun onClickClipBoard() {

            }

            override fun onClickBoard(feedId: Int) {
                //viewModel.onClickBoard(feedId)
            }

            override fun onLongClickBoard(feedId: Int, isHost: Boolean, isAuthor: Boolean) {
                //viewModel.onLongClickBoard(feedId, isHost, isAuthor)
            }

            override val clipBoardList: List<PlubingBoardVo>
                get() = emptyList()
        })
    }

    private val activeGatheringParentAdapter: ActiveGatheringParentAdapter by lazy {
        ActiveGatheringParentAdapter()
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
                adapter = activeGatheringParentAdapter
            }

        }
        viewModel.setPlubIdAndStateType(activeGatheringFragmentArgs.plubbingId, activeGatheringFragmentArgs.stateType)
        viewModel.setTopView()
        //TODO viewModel.getMyToDo
        viewModel.getMyPost()
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
                }
            }
        }
    }
}