package com.plub.presentation.ui.main.gathering.my

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentMyGatheringBinding
import com.plub.presentation.ui.main.gathering.my.adapter.MyGatheringsAdapter
import com.plub.presentation.util.PowerMenuUtil
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs


@AndroidEntryPoint
class MyGatheringFragment :
    BaseTestFragment<FragmentMyGatheringBinding, MyGatheringPageState, MyGatheringViewModel>(
        FragmentMyGatheringBinding::inflate
    ) {

    companion object {
        private const val VIEW_PAGER2_PADDING = 30
        private const val POWER_MENU_OFFSET = -59
    }

    override val viewModel: MyGatheringViewModel by viewModels()
    private val myGatheringsAdapter: MyGatheringsAdapter by lazy {
        MyGatheringsAdapter(
            onMyGatheringMeatBallClick = { view, plubbingId ->
                showGatheringMeatBallPowerMenu(view, plubbingId)
            },
            onMyHostingMeatBallClick = { view, plubbingId ->
                showHostingMeatBallPowerMenu(view, plubbingId)
            },
            onContentClick = { plubbingId ->
                viewModel.goToPlubingMain(plubbingId)
            },
            onCreateGatheringClick = {
                viewModel.goToCreate()
            },
            onParticipateGatheringClick = {
                viewModel.goToHome()
            }
        )
    }

    private fun showGatheringMeatBallPowerMenu(view: View, plubbingId: Int) {
        val items = listOf(getString(R.string.my_gathering_quit))

        PowerMenuUtil.getPlubPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            items
        ) { _, _ ->

        }.showAsDropDown(view, POWER_MENU_OFFSET.px, 0)
    }

    private fun showHostingMeatBallPowerMenu(view: View, plubbingId: Int) {
        val items = listOf(
            getString(R.string.my_gathering_setting),
            getString(R.string.my_gathering_quit),
            getString(R.string.my_gathering_kick_out),
            getString(R.string.my_gathering_close)
        )

        PowerMenuUtil.getPlubPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            items
        ) { _, _ ->

        }.showAsDropDown(view, POWER_MENU_OFFSET.px, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initData()
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            viewPager2.apply {
                adapter = myGatheringsAdapter
                ovalDotsIndicator.attachTo(this)

                children.forEach { child ->
                    if (child is RecyclerView) {
                        child.apply {
                            setPadding(VIEW_PAGER2_PADDING.px, 0, VIEW_PAGER2_PADDING.px, 0)
                            clipToPadding = false
                        }
                        return@forEach
                    }
                }

                setPageTransformer { page, position ->
                    val normalizedPosition = abs(position)
                    page.findViewById<View>(R.id.view_shadow)?.let { view ->
                        view.alpha = normalizedPosition
                    }
                }
            }
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.isRadioButtonMyGatheringChecked.collectLatest { isChecked ->
                    viewModel.onClickRadioButtonMyGathering(isChecked)
                }
            }

            launch {
                viewModel.uiState.isRadioButtonMyHostChecked.collectLatest { isChecked ->
                    viewModel.onClickRadioButtonHost(isChecked)
                }
            }

            launch {
                viewModel.uiState.myGatherings.collect {
                    myGatheringsAdapter.submitList(it)
                    binding.ovalDotsIndicator.setSingleItemIndicatorSize(it)
                }
            }

            launch {
                viewModel.eventFlow.collect { event ->
                    if (event is MyGatheringEvent) inspectEventFlow(event)
                }
            }
        }
    }

    private fun inspectEventFlow(event: MyGatheringEvent) {
        when (event) {
            is MyGatheringEvent.GoToPlubingMain -> {
                val action =
                    MyGatheringFragmentDirections.actionMyGatheringToPlubingMain(event.plubingId)
                findNavController().navigate(action)
            }

            MyGatheringEvent.GoToCreateGathering -> {
                val action = MyGatheringFragmentDirections.actionMyGatheringToCreate()
                findNavController().navigate(action)
            }

            MyGatheringEvent.GoToPlubingHome -> {
                val action = MyGatheringFragmentDirections.actionMyGatheringToPlubingHome()
                findNavController().navigate(action)
            }
        }
    }

}