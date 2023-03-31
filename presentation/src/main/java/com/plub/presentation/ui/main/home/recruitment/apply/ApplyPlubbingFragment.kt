package com.plub.presentation.ui.main.home.recruitment.apply

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentApplyPlubbingBinding
import com.plub.presentation.ui.common.decoration.VerticalSpaceDecoration
import com.plub.presentation.ui.main.home.recruitment.apply.adapter.QuestionsAdapter
import com.plub.presentation.ui.main.home.recruitment.dialog.RecruitApplySuccessDialogFragment
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApplyPlubbingFragment :
    BaseFragment<FragmentApplyPlubbingBinding, ApplyPageState, ApplyPlubbingViewModel>(
        FragmentApplyPlubbingBinding::inflate
    ) {

    companion object{
        const val ITEM_VERTICAL_SPACE = 8
        const val SUCCESS_APPLY_RECRUIT_TAG = "RecruitApplySuccessDialog"
    }

    private val questionsAdapter: QuestionsAdapter by lazy {
        QuestionsAdapter(object : QuestionsAdapter.QuestionsDelegate {
            override fun textChanged(questionId: Int, changedText: String) {
                viewModel.textChanged(questionId, changedText)
            }
        })
    }

    private val recruitApplySuccessDialog : RecruitApplySuccessDialogFragment by lazy {
        RecruitApplySuccessDialogFragment(object : RecruitApplySuccessDialogFragment.Delegate{
            override fun closeButtonClick() {
                viewModel.backPage()
            }
        })
    }

    val applyPlubbingFragmentArgs: ApplyPlubbingFragmentArgs by navArgs()
    override val viewModel: ApplyPlubbingViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            initRecycler()
        }

        viewModel.fetchQuestions(applyPlubbingFragmentArgs.plubbingId, applyPlubbingFragmentArgs.pageType)
    }

    private fun initRecycler() {
        binding.apply {
            recyclerViewQuestions.apply {
                addItemDecoration(VerticalSpaceDecoration(ITEM_VERTICAL_SPACE.px))
                layoutManager = LinearLayoutManager(context)
                adapter = questionsAdapter
            }
        }
    }

    override fun initStates() {
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    questionsAdapter.submitList(it.questions)
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as ApplyEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: ApplyEvent) {
        when (event) {
            is ApplyEvent.BackPage -> {
                findNavController().popBackStack()
            }
            is ApplyEvent.ShowSuccessDialog -> {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        recruitApplySuccessDialog.show(childFragmentManager,SUCCESS_APPLY_RECRUIT_TAG)
    }
}