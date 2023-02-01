package com.plub.presentation.ui.main.home.recruitment.apply

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitAnswerListVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentApplyPlubbingBinding
import com.plub.presentation.event.ApplyEvent
import com.plub.presentation.ui.main.home.recruitment.apply.adapter.QuestionsAdapter
import com.plub.presentation.ui.main.home.recruitment.dialog.ApplySuccessDialog
import com.plub.presentation.ui.main.home.recruitment.RecruitmentFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApplyPlubbingFragment :
    BaseFragment<FragmentApplyPlubbingBinding, ApplyPageState, ApplyPlubbingViewModel>(
        FragmentApplyPlubbingBinding::inflate
    ) {

    private val questionsAdapter: QuestionsAdapter by lazy {
        QuestionsAdapter(object : QuestionsAdapter.QuestionsDegelate {
            override fun isNotEmpty() {
                viewModel.isEmpty(questionsAdapter, binding.recyclerViewQuestions)
            }
        })
    }

    val recruitArgs: RecruitmentFragmentArgs by navArgs()
    override val viewModel: ApplyPlubbingViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            initRecycler()
            buttonApply.setOnClickListener {
                viewModel.applyRecruit(recruitArgs.plubbingId.toInt(), getAnswerList())
            }
        }
        viewModel.fetchQuestions(recruitArgs.plubbingId.toInt())
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

    private fun getAnswerList(): List<ApplicantsRecruitAnswerListVo> {
        return viewModel.getAnswerList(questionsAdapter, binding.recyclerViewQuestions)
    }

    private fun initRecycler() {
        binding.apply {
            recyclerViewQuestions.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = questionsAdapter
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
        ApplySuccessDialog(requireContext()) {
            viewModel.backPage()
        }.show()
    }
}