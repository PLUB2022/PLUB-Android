package com.plub.presentation.ui.main.gathering.modifyGathering

import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.plub.domain.model.enums.ModifyGatheringPageType
import com.plub.domain.model.enums.PlubingMainPageType
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentModifyGatheringBinding
import com.plub.presentation.databinding.IncludeTabModifyGatheringBinding
import com.plub.presentation.databinding.IncludeTabPlubingMainBinding
import com.plub.presentation.ui.Event
import com.plub.presentation.ui.main.gathering.modifyGathering.adapter.FragmentModifyGatheringPagerAdapter
import com.plub.presentation.ui.main.plubing.adapter.FragmentPlubingMainPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModifyGatheringFragment : BaseFragment
<FragmentModifyGatheringBinding, ModifyGatheringPageState, ModifyGatheringViewModel>(
    FragmentModifyGatheringBinding::inflate
) {
    override val viewModel: ModifyGatheringViewModel by viewModels()

    private var pagerAdapter: FragmentModifyGatheringPagerAdapter? = null

    override fun initView() {
        binding.apply {
            vm = viewModel
        }

        viewModel.getGatheringInfoDetail(21)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {

            launch {
                viewModel.uiState.collect { uiState ->
                    viewModel.handleUiState(uiState)
                }
            }

            launch {
                viewModel.eventFlow.collect { event ->
                    if(event is ModifyGatheringEvent) inspectEventFlow(event)
                }
            }

        }
    }

    private fun inspectEventFlow(event: ModifyGatheringEvent) {
        when(event) {
            is ModifyGatheringEvent.InitViewPager -> { initViewPager() }
        }
    }

    private fun initViewPager() = binding.apply {
        if(pagerAdapter != null) return@apply

        pagerAdapter = FragmentModifyGatheringPagerAdapter(this@ModifyGatheringFragment, viewModel.uiState.value)

        viewPagerModifyGathering.apply {
            adapter = pagerAdapter
            isUserInputEnabled = false
        }

        TabLayoutMediator(tabLayoutModifyGathering, viewPagerModifyGathering) { tab, position ->
            tab.customView = getTabView(position)
        }.attach()
    }

    private fun getTabView(tabIndex: Int): View {
        return IncludeTabModifyGatheringBinding.inflate(layoutInflater).apply {
            textViewTab.text = getTabTitleString(tabIndex)
        }.root
    }

    private fun getTabTitleString(tabIndex: Int): String {
        return when (ModifyGatheringPageType.valueOf(tabIndex)) {
            ModifyGatheringPageType.RECRUIT -> getString(R.string.modify_gathering_tab_recruit)
            ModifyGatheringPageType.INFO -> getString(R.string.modify_gathering_tab_info)
            ModifyGatheringPageType.GUEST -> getString(R.string.modify_gathering_tab_guest)
        }
    }
}