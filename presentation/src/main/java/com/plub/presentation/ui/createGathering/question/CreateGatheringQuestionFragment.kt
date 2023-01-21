package com.plub.presentation.ui.createGathering.question

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringQuestionBinding
import com.plub.presentation.ui.createGathering.CreateGatheringEvent
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import com.plub.presentation.ui.createGathering.dayAndOnOfflineAndLocation.CreateGatheringDayAndTimeAndOnOfflineAndLocationEvent
import com.plub.presentation.ui.createGathering.peopleNumber.CreateGatheringPeopleNumberPageState
import com.plub.presentation.ui.createGathering.question.adapter.QuestionRecyclerViewAdapter
import com.plub.presentation.ui.createGathering.question.bottomSheet.BottomSheetDeleteQuestion
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateGatheringQuestionFragment : BaseFragment<
        FragmentCreateGatheringQuestionBinding, CreateGatheringQuestionPageState, CreateGatheringQuestionViewModel>(
    FragmentCreateGatheringQuestionBinding::inflate
) {
    override val viewModel: CreateGatheringQuestionViewModel by viewModels()
    private val parentViewModel: CreateGatheringViewModel by viewModels({ requireParentFragment() })
    private lateinit var questionRecyclerViewAdapter: QuestionRecyclerViewAdapter

    override fun initView() {
        binding.apply {
            vm = viewModel
            parentVm = parentViewModel

            questionRecyclerViewAdapter =
                QuestionRecyclerViewAdapter(
                    onClickDeleteButton = { position ->
                        viewModel.onClickRecyclerViewDeleteButton(position)
                    },
                    updateEditText = { data, text ->
                        viewModel.updateQuestion(data, text)
                    }
                )

            questionRecyclerViewAdapter.setHasStableIds(true)
            recyclerViewQuestion.adapter = questionRecyclerViewAdapter
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                if (viewModel.uiState.value != CreateGatheringQuestionPageState())
                    return@launch

                parentViewModel.childrenPageStateFlow.collect { pageState ->
                    if (pageState is CreateGatheringQuestionPageState)
                        viewModel.initUiState(pageState)
                }
            }

            launch {
                parentViewModel.eventFlow.collect {
                    if(viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@collect

                    when (it) {
                        is CreateGatheringEvent.GoToPrevPage -> {
                            parentViewModel.setChildrenPageState(viewModel.uiState.value)
                            parentViewModel.goToPrevPageAndEmitChildrenPageState()
                        }
                    }
                }
            }

            launch {
                viewModel.uiState.collectLatest { pageState ->
                    if (pageState.needUpdateRecyclerView.not()) return@collectLatest
                    questionRecyclerViewAdapter.submitList(pageState.questions)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    when(it) {
                        is CreateGatheringQuestionEvent.ShowBottomSheetDeleteQuestion -> {
                            showBottomSheetDeleteQuestion(it)
                        }

                        is CreateGatheringQuestionEvent.PerformClickNoQuestionRadioButton -> {
                            binding.radioButtonNoQuestion.performClick()
                        }
                    }
                }
            }
        }
    }

    private fun showBottomSheetDeleteQuestion(it: CreateGatheringQuestionEvent.ShowBottomSheetDeleteQuestion) {
        val bottomSheetDeleteQuestion = BottomSheetDeleteQuestion(
            it.position,
            it.size
        ) { _ ->
            viewModel.onClickBottomSheetDelete(it.position, it.size)
        }

        bottomSheetDeleteQuestion.show(
            requireActivity().supportFragmentManager,
            bottomSheetDeleteQuestion.tag
        )
    }
}