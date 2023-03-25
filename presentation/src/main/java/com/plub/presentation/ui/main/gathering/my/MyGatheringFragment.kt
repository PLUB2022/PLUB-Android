package com.plub.presentation.ui.main.gathering.my

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentMyGatheringBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyGatheringFragment : BaseTestFragment<FragmentMyGatheringBinding, MyGatheringPageState, MyGatheringViewModel>(
    FragmentMyGatheringBinding::inflate
) {
    override val viewModel: MyGatheringViewModel by viewModels()

    override fun initView() {

    }
}