package com.plub.presentation.ui.main.gathering.create.question

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringQuestionBinding
import com.plub.presentation.ui.main.gathering.create.CreateGatheringEvent
import com.plub.presentation.ui.main.gathering.create.CreateGatheringViewModel
import com.plub.presentation.ui.main.gathering.create.question.adapter.QuestionRecyclerViewAdapter
import com.plub.presentation.ui.main.gathering.create.question.bottomSheet.BottomSheetDeleteQuestion
import dagger.hilt.android.AndroidEntryPoint
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
                parentViewModel.childrenPageStateFlow.collect { pageState ->
                    viewModel.initUiState(pageState)
                }
            }

            launch {
                parentViewModel.eventFlow.collect {
                    if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@collect

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
                    when (it) {
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
        val bottomSheetDeleteQuestion = BottomSheetDeleteQuestion.newInstance(
            position = it.position,
            questionCount = it.size
        ) { _ ->
            viewModel.onClickBottomSheetDelete(it.size, it.position)
        }

        bottomSheetDeleteQuestion.show(
            parentFragmentManager,
            bottomSheetDeleteQuestion.tag
        )
    }
}