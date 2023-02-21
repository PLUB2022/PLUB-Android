package com.plub.presentation.ui.main.gathering.modifyGathering.guestQuestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentModifyGuestQuestionBinding


class ModifyGuestQuestionFragment : BaseFragment<
        FragmentModifyGuestQuestionBinding, ModifyGuestQuestionPageState, ModifyGuestQuestionViewModel>(
    FragmentModifyGuestQuestionBinding::inflate
) {
    override val viewModel: ModifyGuestQuestionViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }
}