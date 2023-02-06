package com.plub.presentation.ui.main.plubing

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.plub.domain.model.enums.PlubingMainPageType
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPlubingMainBinding
import com.plub.presentation.databinding.IncludeTabPlubingMainBinding
import com.plub.presentation.ui.main.plubing.adapter.FragmentPlubingMainPagerAdapter
import com.plub.presentation.ui.main.plubing.adapter.PlubingMemberAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PlubingMainFragment :
    BaseFragment<FragmentPlubingMainBinding, PlubingMainPageState, PlubingMainViewModel>(
        FragmentPlubingMainBinding::inflate
    ) {

    override val viewModel: PlubingMainViewModel by viewModels()
    private val plubingArgs: PlubingMainFragmentArgs by navArgs()

    private val pagerAdapter: FragmentPlubingMainPagerAdapter by lazy {
        FragmentPlubingMainPagerAdapter(this, plubingArgs.plubingId)
    }

    private val memberListAdapter: PlubingMemberAdapter by lazy {
        PlubingMemberAdapter(object : PlubingMemberAdapter.Delegate {
            override fun onClickProfile(id: Int) {
                viewModel.onClickProfile(id)
            }
        })
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            viewPagerPlubMain.apply {
                isUserInputEnabled = false
                adapter = pagerAdapter
            }

            includeMainTop.recyclerViewMembers.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
                adapter = memberListAdapter
            }

            appBarLayout.addOnOffsetChangedListener { appBarLayout, _ ->
                viewModel.onAppBarOffsetChanged(appBarLayout.y, appBarLayout.totalScrollRange)
            }

            TabLayoutMediator(tabLayoutPlubMain, viewPagerPlubMain) { tab, position ->
                tab.customView = getTabView(position)
            }.attach()
        }
        viewModel.onFetchPlubingMainInfo(plubingArgs.plubingId)
    }

    private fun getTabView(tabIndex: Int): View {
        return IncludeTabPlubingMainBinding.inflate(layoutInflater).apply {
            textViewTab.text = getTabTitleString(tabIndex)
        }.root
    }

    private fun getTabTitleString(tabIndex: Int): String {
        return when (PlubingMainPageType.valueOf(tabIndex)) {
            PlubingMainPageType.BOARD -> getString(R.string.plubing_main_tab_board)
            PlubingMainPageType.TODO_LIST -> getString(R.string.plubing_main_tab_todo_list)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.collect {
                    memberListAdapter.submitList(it.memberList)
                }
            }
        }
    }
}