package com.plub.presentation.ui.home.plubing.recruitment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentApplyPlubbingBinding
import com.plub.presentation.state.PageState
import com.plub.presentation.ui.home.plubing.recruitment.adapter.QuestionsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApplyPlubbingFragment : BaseFragment<FragmentApplyPlubbingBinding, PageState.Default, ApplyPlubbingViewModel>(
    FragmentApplyPlubbingBinding::inflate
)  {
    private val questionsAdapter: QuestionsAdapter by lazy {
        QuestionsAdapter(object : QuestionsAdapter.QuestionsDegelate {
            override fun onProfileClick(accountId: Int) {
                //
            }
        })
    }

    val recruitArgs : RecruitmentFragmentArgs by navArgs()
    override val viewModel: ApplyPlubbingViewModel by viewModels()

    override fun initView() {

        binding.apply {
            vm = viewModel
            initRecycler()
            buttonApply.setOnClickListener {
                viewModel.applyRecruit(recruitArgs.plubbingId.toInt())
            }
        }
        viewModel.fetchQuestions(recruitArgs.plubbingId.toInt())
    }

    override fun initStates() {
        //TODO("Not yet implemented")
        super.initStates()
        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.recruitQuestionData.collect{
                    questionsAdapter.submitList(it.questions)
                }
            }

        }
    }

    fun initRecycler(){
        binding.apply {
            recyclerViewQuestions.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = questionsAdapter
            }
        }
    }
}