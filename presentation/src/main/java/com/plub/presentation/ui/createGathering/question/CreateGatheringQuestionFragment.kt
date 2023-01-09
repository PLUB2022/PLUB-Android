package com.plub.presentation.ui.createGathering.question

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.SimpleItemAnimator
import com.plub.domain.model.state.PageState
import com.plub.domain.model.state.createGathering.CreateGatheringQuestionPageState
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringQuestionBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
import com.plub.presentation.ui.createGathering.question.adapter.QuestionRecyclerViewAdapter
import com.plub.presentation.ui.createGathering.question.bottomSheet.BottomSheetDeleteQuestion
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
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
                viewModel.uiState.collectLatest { pageState ->
                    if(pageState.needUpdateRecyclerView.not()) return@collectLatest
                    PlubLogger.logD("테스트", "이전 값 ${questionRecyclerViewAdapter.currentList}")
                    questionRecyclerViewAdapter.submitList(pageState.questions) {
                        PlubLogger.logD("테스트", "이후 값 ${questionRecyclerViewAdapter.currentList}")
                    }
                }
            }

            launch {
                viewModel.showBottomSheetDeleteQuestion.collectLatest { position ->
                    val bottomSheetDeleteQuestion = BottomSheetDeleteQuestion(position) { position ->
                        viewModel.deleteQuestion(position)
                    }

                    bottomSheetDeleteQuestion.show(
                        requireActivity().supportFragmentManager,
                        bottomSheetDeleteQuestion.tag
                    )
                }
            }
        }
    }
}