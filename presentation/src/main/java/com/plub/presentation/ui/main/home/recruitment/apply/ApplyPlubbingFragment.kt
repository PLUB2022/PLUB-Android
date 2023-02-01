package com.plub.presentation.ui.main.home.recruitment.apply

import android.widget.EditText
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitAnswerListVo
import com.plub.presentation.R
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
            override fun isNotEmpty(flag: Boolean) {
                viewModel.updateButtonState(flag)
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
        val list: MutableList<ApplicantsRecruitAnswerListVo> = mutableListOf()
        for (i in 1 until questionsAdapter.itemCount) {
            list.add(
                ApplicantsRecruitAnswerListVo(
                    questionsAdapter.currentList[i].id,
                    binding.recyclerViewQuestions.get(i)
                        .findViewById<EditText>(R.id.edit_text_answer).text.toString()
                )
            )
        }
        return list
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