package com.plub.presentation.ui.main.gathering.modify.info

import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.plub.domain.model.enums.DialogMenuType
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentModifyInfoBinding
import com.plub.presentation.ui.common.bindingAdapter.updateSeekBarProgressAndPosition
import com.plub.presentation.ui.common.dialog.SelectMenuBottomSheetDialog
import com.plub.presentation.ui.main.gathering.create.dayAndOnOfflineAndLocation.CreateGatheringDayAndTimeAndOnOfflineAndLocationEvent
import com.plub.presentation.ui.main.gathering.create.dayAndOnOfflineAndLocation.bottomSheet.BottomSheetSearchLocation
import com.plub.presentation.util.IntentUtil
import com.plub.presentation.util.PermissionManager
import com.plub.presentation.util.parcelable
import com.plub.presentation.util.px
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModifyInfoFragment : BaseFragment<
        FragmentModifyInfoBinding, ModifyInfoPageState, ModifyInfoViewModel>(
    FragmentModifyInfoBinding::inflate
) {
    override val viewModel: ModifyInfoViewModel by viewModels()
    private val navArgs: ModifyInfoFragmentArgs by navArgs()

    private val timePickerDialog: TimePickerDialog by lazy {
        TimePickerDialog(
            requireActivity(),
            { _, hour, min -> viewModel.setGatheringHourAndMinuteAndFormattedText(hour, min) },
            viewModel.uiState.value.gatheringHour,
            viewModel.uiState.value.gatheringMin,
            false
        )
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
        }

        binding.seekBarPeople.post {
            viewModel.initPageState(
                navArgs.pageState,
                (binding.seekBarPeople.measuredWidth * navArgs.pageState.seekBarProgress /  binding.seekBarPeople.max -  binding.seekBarPeople.thumbOffset).toFloat()
            )
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    when (it) {
                        is ModifyInfoEvent.ShowBottomSheetSearchLocation -> {
                            showBottomSheetLocation()
                        }

                        is ModifyInfoEvent.ShowTimePickerDialog -> {
                            timePickerDialog.show()
                        }
                        is ModifyInfoEvent.GoToBack -> {
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }

    private fun showBottomSheetLocation() {
        val bottomSheetSearchLocation = BottomSheetSearchLocation.newInstance { data ->
            viewModel.updateGatheringLocationData(data)
        }
        bottomSheetSearchLocation.show(
            parentFragmentManager,
            bottomSheetSearchLocation.tag
        )
    }
}
