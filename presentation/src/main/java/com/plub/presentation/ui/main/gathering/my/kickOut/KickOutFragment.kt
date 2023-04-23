package com.plub.presentation.ui.main.gathering.my.kickOut

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentKickOutBinding
import com.plub.presentation.ui.common.dialog.CommonDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KickOutFragment : BaseTestFragment<FragmentKickOutBinding, KickOutPageState, KickOutViewModel>(
    FragmentKickOutBinding::inflate
) {
    @Inject
    lateinit var commonDialog: CommonDialog

    override val viewModel: KickOutViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }
}