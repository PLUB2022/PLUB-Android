package com.plub.presentation.ui.main.plubing.schedule

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPlubingScheduleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlubingScheduleFragment : BaseFragment<
        FragmentPlubingScheduleBinding, PlubingSchedulePageState, PlubingScheduleViewModel>(
    FragmentPlubingScheduleBinding::inflate
) {
    override val viewModel: PlubingScheduleViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }
}