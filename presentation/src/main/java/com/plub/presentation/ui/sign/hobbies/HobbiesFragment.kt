package com.plub.presentation.ui.sign.hobbies

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.SignUpPageType
import com.plub.domain.model.vo.common.SelectedHobbyVo
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentHobbiesBinding
import com.plub.presentation.state.HobbiesPageState
import com.plub.presentation.ui.common.VerticalSpaceDecoration
import com.plub.presentation.ui.dialog.adapter.DialogMenuAdapter
import com.plub.presentation.ui.sign.hobbies.adapter.HobbiesAdapter
import com.plub.presentation.ui.sign.signup.SignUpViewModel
import com.plub.presentation.util.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HobbiesFragment : BaseFragment<FragmentHobbiesBinding, HobbiesPageState, HobbiesViewModel>(
    FragmentHobbiesBinding::inflate
) {

    companion object {
        private const val ITEM_VERTICAL_SPACE = 16
    }

    override val viewModel: HobbiesViewModel by viewModels()
    private val parentViewModel: SignUpViewModel by viewModels({requireParentFragment()})
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

            recyclerViewHobbies.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = listAdapter
                addItemDecoration(VerticalSpaceDecoration(ITEM_VERTICAL_SPACE.dp))
            }
        }
        viewModel.fetchHobbiesData()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {

            launch {
                viewModel.moveToNextPage.collect {
                    parentViewModel.onMoveToNextPage(SignUpPageType.HOBBY, it)
                }
            }

            launch {
                parentViewModel.uiState.collect {
                    viewModel.onSaveNickname(it.profileComposeVo.nickname)
                    viewModel.onInitHobbyInfo(it.hobbyInfoVo)
                }
            }

            launch {
                viewModel.uiState.collect {
                    updateSelectedHobbies(it.hobbiesSelectedVo.hobbies)
                    listAdapter.submitList(it.hobbiesVo)
                }
            }

            launch {
                viewModel.notifySubHobby.collect {
                    listAdapter.notifySubItemUpdate(it)
                }
            }

            launch {
                viewModel.notifyAllHobby.collect {
                    listAdapter.notifyAllItemUpdate()
                }
            }
        }
    }

    private fun updateSelectedHobbies(hobbies:List<SelectedHobbyVo>) {
        selectedHobbiesList.clear()
        selectedHobbiesList.addAll(hobbies)
    }
}