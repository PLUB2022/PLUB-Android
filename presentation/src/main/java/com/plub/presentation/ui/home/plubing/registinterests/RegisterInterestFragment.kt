package com.plub.presentation.ui.home.plubing.registinterests

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.SignUpPageType
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentInterestsRegisterBinding
import com.plub.presentation.event.HobbiesEvent
import com.plub.presentation.state.HobbiesPageState
import com.plub.presentation.state.PageState
import com.plub.presentation.ui.common.VerticalSpaceDecoration
import com.plub.presentation.ui.sign.hobbies.HobbiesViewModel
import com.plub.presentation.ui.sign.hobbies.adapter.HobbiesAdapter
import com.plub.presentation.ui.sign.signup.SignUpViewModel
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
    private val selectedHobbiesList:MutableList<SelectedHobbyVo> = mutableListOf()

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
                viewModel.onClickLatePick()
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
                    inspectEventFlow(it as HobbiesEvent)
                }
            }

            launch {
                viewModel.uiState.collect {
                    updateSelectedHobbies(it.hobbiesSelectedVo.hobbies)
                    listAdapter.submitList(it.hobbiesVo)
                }
            }
        }
    }

    private fun inspectEventFlow(event: HobbiesEvent) {
        when(event) {
            is HobbiesEvent.NotifyAllHobby -> {
                listAdapter.notifyAllItemUpdate()
            }
            is HobbiesEvent.NotifySubHobby -> {
                listAdapter.notifySubItemUpdate(event.vo)
            }
//            is HobbiesEvent.MoveToNext -> {
//                parentViewModel.onMoveToNextPage(SignUpPageType.HOBBY, event.vo)
//            }
            else -> {}
        }
    }

    private fun updateSelectedHobbies(hobbies:List<SelectedHobbyVo>) {
        selectedHobbiesList.clear()
        selectedHobbiesList.addAll(hobbies)
    }

}