package com.plub.presentation.ui.main.registinterests

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentInterestsRegisterBinding
import com.plub.presentation.event.Event
import com.plub.presentation.event.HobbiesEvent
import com.plub.presentation.event.RegisterInterestEvent
import com.plub.presentation.ui.common.decoration.VerticalSpaceDecoration
import com.plub.presentation.ui.sign.hobbies.HobbiesPageState
import com.plub.presentation.ui.sign.hobbies.adapter.HobbiesAdapter
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterInterestFragment : BaseFragment<FragmentInterestsRegisterBinding, HobbiesPageState, RegisterInterestViewModel>(
    FragmentInterestsRegisterBinding::inflate
)  {
    companion object {
        private const val ITEM_VERTICAL_SPACE = 8
    }

    override val viewModel: RegisterInterestViewModel by viewModels()

    private val listAdapter: HobbiesAdapter by lazy {
        HobbiesAdapter(object : HobbiesAdapter.Delegate {
            override val selectedList: List<SelectedHobbyVo>
                get() = viewModel.uiState.value.hobbiesSelectedVo.hobbies

            override fun onClickExpand(hobbyId: Int) {
                viewModel.onClickExpand(hobbyId)
            }

            override fun onClickSubHobby(isClicked: Boolean, selectedHobbyVo: SelectedHobbyVo) {
                viewModel.onClickSubHobby(isClicked, selectedHobbyVo)
            }

            override fun onClickLatePick() {

            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel


            recyclerViewInterestsCategory.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = listAdapter
                addItemDecoration(VerticalSpaceDecoration(ITEM_VERTICAL_SPACE.px))
            }
        }
        viewModel.fetchHobbiesData()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it)
                }
            }

            launch {
                viewModel.uiState.collect {
                    listAdapter.submitList(it.hobbiesVo)
                }
            }
            launch {
                viewModel.emitChoice.collect{
                    setInterestList(viewModel.uiState.value.hobbiesSelectedVo.hobbies)
                    moveMainPage()
                }
            }
        }
    }

    private fun inspectEventFlow(event: Event) {
        when(event) {
            is HobbiesEvent.NotifyAllHobby -> {
                listAdapter.notifyAllItemUpdate()
            }
            is HobbiesEvent.NotifySubHobby -> {
                listAdapter.notifySubItemUpdate(event.vo)
            }
            is RegisterInterestEvent.BackPage->{
                findNavController().popBackStack()
            }
        }
    }


    private fun setInterestList(list : List<SelectedHobbyVo>){
        viewModel.registerInterest(list)
    }

    private fun moveMainPage(){
        findNavController().popBackStack()
    }

}