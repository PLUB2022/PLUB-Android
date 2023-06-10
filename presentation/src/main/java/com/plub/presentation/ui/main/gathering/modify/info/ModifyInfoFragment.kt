package com.plub.presentation.ui.main.gathering.modify.info

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentModifyInfoBinding
import com.plub.presentation.databinding.FragmentModifyRecruitBinding
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.PermissionManager
import com.plub.presentation.util.parcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModifyInfoFragment : BaseFragment<
        FragmentModifyInfoBinding, ModifyInfoPageState, ModifyInfoViewModel>(
    FragmentModifyInfoBinding::inflate
) {
    override val viewModel: ModifyInfoViewModel by viewModels()
    private val navArgs: ModifyInfoFragmentArgs by navArgs()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }

        // viewModel.initPageState(navArgs.pageState)
    }

    fun getSeekBarPositionX(): Float {
        return (((binding.seekBarPeople.width - (binding.seekBarPeople.paddingStart + binding.seekBarPeople.paddingEnd)) / binding.seekBarPeople.max) * navArgs.pageState.seekBarProgress).toFloat()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    when (it) {

                    }
                }
            }
        }
    }
}
