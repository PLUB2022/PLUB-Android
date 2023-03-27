package com.plub.presentation.ui.main.gathering.my

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.plub.domain.model.enums.MyGatheringsViewType
import com.plub.domain.model.vo.myGathering.MyGatheringResponseVo
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentMyGatheringBinding
import com.plub.presentation.ui.main.gathering.my.adapter.MyGatheringsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyGatheringFragment :
    BaseTestFragment<FragmentMyGatheringBinding, MyGatheringPageState, MyGatheringViewModel>(
        FragmentMyGatheringBinding::inflate
    ) {
    override val viewModel: MyGatheringViewModel by viewModels()
    private val recyclerViewAdapter: MyGatheringsAdapter by lazy {
        MyGatheringsAdapter(
            onMyGatheringMeatBallClick = { },
            onMyHostingMeatBallClick = { },
            onCreateGatheringClick = { },
            onParticipateGatheringClick = { }
        )
    }

    override fun initView() {
        binding.apply {
            vm = viewModel

            recyclerView.apply {
                adapter = recyclerViewAdapter
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }

                val snapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(this)
            }

            recyclerViewAdapter.submitList(
                listOf(
                    MyGatheringResponseVo(),
                    MyGatheringResponseVo(),
                    MyGatheringResponseVo(viewType = MyGatheringsViewType.PARTICIPATE)
                )
            )
        }
    }
}