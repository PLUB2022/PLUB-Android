package com.plub.presentation.ui.main.gathering.modifyGathering.recruit

import android.os.Bundle
import android.os.Parcel
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentModifyRecruitBinding
import com.plub.presentation.ui.main.gathering.createGathering.question.bottomSheet.BottomSheetDeleteQuestion
import com.plub.presentation.util.parcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyRecruitFragment : BaseFragment<
        FragmentModifyRecruitBinding, ModifyRecruitPageState, ModifyRecruitViewModel>(
    FragmentModifyRecruitBinding::inflate
) {
    override val viewModel: ModifyRecruitViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }

        val pageState = arguments?.parcelable(MODIFY_RECRUIT_PAGE_STATE) ?: ModifyRecruitPageState()
        viewModel.initPageState(pageState)
    }

    companion object {
        private const val MODIFY_RECRUIT_PAGE_STATE = "MODIFY_RECRUIT_PAGE_STATE"

        fun newInstance(
            initPageState: ModifyRecruitPageState
        ) = ModifyRecruitFragment().apply {
            arguments = Bundle().apply {
                putParcelable(MODIFY_RECRUIT_PAGE_STATE, initPageState)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        arguments?.putParcelable(MODIFY_RECRUIT_PAGE_STATE, viewModel.uiState.value)
    }
}
