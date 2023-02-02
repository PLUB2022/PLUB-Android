package com.plub.presentation.ui.main.plubing

import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.plub.domain.model.enums.PlubingMainPageType
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPlubingMainBinding
import com.plub.presentation.databinding.IncludeTabPlubingMainBinding
import com.plub.presentation.ui.main.plubing.adapter.FragmentPlubingMainPagerAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlubingMainFragment :
    BaseFragment<FragmentPlubingMainBinding, PlubingMainPageState, PlubingMainViewModel>(
        FragmentPlubingMainBinding::inflate
    ) {

    override val viewModel: PlubingMainViewModel by viewModels()

    private val pagerAdapter: FragmentPlubingMainPagerAdapter by lazy {
        FragmentPlubingMainPagerAdapter(this)
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            viewPagerPlubMain.apply {
                isUserInputEnabled = false
                adapter = pagerAdapter
            }

            appBarLayout.addOnOffsetChangedListener { appBarLayout, _ ->
                viewModel.onAppBarOffsetChanged(appBarLayout.y, appBarLayout.totalScrollRange)
            }

            TabLayoutMediator(tabLayoutPlubMain, viewPagerPlubMain) { tab, position ->
                tab.customView = getTabView(position)
            }.attach()
        }
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

        }
    }
}