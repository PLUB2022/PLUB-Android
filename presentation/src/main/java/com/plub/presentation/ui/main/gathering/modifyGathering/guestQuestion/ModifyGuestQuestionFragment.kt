package com.plub.presentation.ui.main.gathering.modifyGathering.guestQuestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentModifyGuestQuestionBinding
import com.plub.presentation.ui.main.gathering.createGathering.question.CreateGatheringQuestionEvent
import com.plub.presentation.ui.main.gathering.createGathering.question.adapter.QuestionRecyclerViewAdapter
import com.plub.presentation.ui.main.gathering.createGathering.question.bottomSheet.BottomSheetDeleteQuestion
import com.plub.presentation.ui.main.gathering.modifyGathering.recruit.ModifyRecruitFragment
import com.plub.presentation.ui.main.gathering.modifyGathering.recruit.ModifyRecruitPageState
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

        val pageState = arguments?.parcelable(MODIFY_GUEST_QUESTION_PAGE_STATE) ?: ModifyGuestQuestionPageState()
        viewModel.initPageState(pageState)
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
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        arguments?.putParcelable(MODIFY_GUEST_QUESTION_PAGE_STATE, viewModel.uiState.value)
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

    companion object {
        private const val MODIFY_GUEST_QUESTION_PAGE_STATE = "MODIFY_GUEST_QUESTION_PAGE_STATE"

        fun newInstance(
            initPageState: ModifyGuestQuestionPageState
        ) = ModifyGuestQuestionFragment().apply {
            arguments = Bundle().apply {
                putParcelable(MODIFY_GUEST_QUESTION_PAGE_STATE, initPageState)
            }
        }
    }
}