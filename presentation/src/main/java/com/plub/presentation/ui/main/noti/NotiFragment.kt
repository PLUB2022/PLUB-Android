package com.plub.presentation.ui.main.noti

import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentNotiBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotiFragment : BaseTestFragment<FragmentNotiBinding, NotiPageState, NotiViewModel>(
    FragmentNotiBinding::inflate
) {
    override val viewModel: NotiViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }
}