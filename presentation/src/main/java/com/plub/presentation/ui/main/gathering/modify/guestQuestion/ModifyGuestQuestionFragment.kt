package com.plub.presentation.ui.main.gathering.modify.guestQuestion

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentModifyGuestQuestionBinding
import com.plub.presentation.ui.main.gathering.create.question.adapter.QuestionRecyclerViewAdapter
import com.plub.presentation.ui.main.gathering.create.question.bottomSheet.BottomSheetDeleteQuestion
import com.plub.presentation.util.parcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModifyGuestQuestionFragment : BaseFragment<
        FragmentModifyGuestQuestionBinding, ModifyGuestQuestionPageState, ModifyGuestQuestionViewModel>(
    FragmentModifyGuestQuestionBinding::inflate
) {
    override val viewModel: ModifyGuestQuestionViewModel by viewModels()
    private lateinit var questionRecyclerViewAdapter: QuestionRecyclerViewAdapter

    private val navArgs: ModifyGuestQuestionFragmentArgs by navArgs()

    override fun initView() {
        binding.apply {
            vm = viewModel

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

        viewModel.initPageState(navArgs.pageState)
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
                viewModel.eventFlow.collect {
                    when (it) {
                        is ModifyGuestQuestionEvent.ShowBottomSheetDeleteQuestion -> {
                            showBottomSheetDeleteQuestion(it)
                        }

                        is ModifyGuestQuestionEvent.PerformClickNoQuestionRadioButton -> {
                            binding.radioButtonNoQuestion.performClick()
                        }
                        is ModifyGuestQuestionEvent.GoToBack -> {
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }

    private fun showBottomSheetDeleteQuestion(it: ModifyGuestQuestionEvent.ShowBottomSheetDeleteQuestion) {
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