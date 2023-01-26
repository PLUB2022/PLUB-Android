package com.plub.presentation.ui.home.plubing.recruitment

import android.util.Log
import android.widget.EditText
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.home.applicantsrecruitvo.ApplicantsRecruitAnswerListVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentApplyPlubbingBinding
import com.plub.presentation.state.ApplyPageState
import com.plub.presentation.state.PageState
import com.plub.presentation.ui.home.plubing.recruitment.adapter.QuestionsAdapter
import com.plub.presentation.ui.home.plubing.recruitment.adapter.QuestionsViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApplyPlubbingFragment : BaseFragment<FragmentApplyPlubbingBinding, ApplyPageState, ApplyPlubbingViewModel>(
    FragmentApplyPlubbingBinding::inflate
)  {

    private val questionsAdapter: QuestionsAdapter by lazy {
        QuestionsAdapter(object : QuestionsAdapter.QuestionsDegelate{
            override fun isNotEmpty(flag: Boolean) {
                viewModel.updateButtonState(flag)
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
                viewModel.applyRecruit(recruitArgs.plubbingId.toInt(), getAnswerList())
                //어디로 가야하지?
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
    fun getAnswerList() : List<ApplicantsRecruitAnswerListVo>{
        val list : MutableList<ApplicantsRecruitAnswerListVo> = mutableListOf()
        for(i in 0 until questionsAdapter.itemCount){
            list.add(ApplicantsRecruitAnswerListVo(questionsAdapter.currentList[i].id,
                binding.recyclerViewQuestions.get(i).findViewById<EditText>(R.id.edit_text_answer).text.toString()))
        }
        return list
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