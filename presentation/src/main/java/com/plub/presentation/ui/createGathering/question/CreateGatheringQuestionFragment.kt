package com.plub.presentation.ui.createGathering.question

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentCreateGatheringQuestionBinding
import com.plub.presentation.ui.createGathering.CreateGatheringViewModel
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
                viewModel.uiState.collectLatest { pageState ->
                    if (pageState.needUpdateRecyclerView.not()) return@collectLatest
                    questionRecyclerViewAdapter.submitList(pageState.questions)
                }
            }

            launch {
                viewModel.showBottomSheetDeleteQuestion.collectLatest { (size, position) ->
                    val bottomSheetDeleteQuestion = BottomSheetDeleteQuestion(
                        position,
                        size
                    ) { _ ->
                        viewModel.onClickBottomSheetDelete(size, position)
                    }

                    bottomSheetDeleteQuestion.show(
                        requireActivity().supportFragmentManager,
                        bottomSheetDeleteQuestion.tag
                    )
                }
            }

            launch {
                viewModel.performClickNoQuestionRadioButton.collect {
                    binding.radioButtonNoQuestion.performClick()
                }
            }
        }
    }
}