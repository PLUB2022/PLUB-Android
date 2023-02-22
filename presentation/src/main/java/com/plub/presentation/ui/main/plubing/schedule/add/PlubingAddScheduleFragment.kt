package com.plub.presentation.ui.main.plubing.schedule.add

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPlubingAddScheduleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlubingAddScheduleFragment : BaseFragment<
        FragmentPlubingAddScheduleBinding, PlubingAddSchedulePageState, PlubingAddScheduleViewModel>(
    FragmentPlubingAddScheduleBinding::inflate
) {
    override val viewModel: PlubingAddScheduleViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }
}