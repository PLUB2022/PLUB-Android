package com.plub.presentation.ui.sign.signup.authentication.bottomSheet

import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.plub.presentation.base.BaseBottomSheetFragment
import com.plub.presentation.databinding.BottomSheetPhoneCertificationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AgainAskNumberBottomSheet :
    BaseBottomSheetFragment<BottomSheetPhoneCertificationBinding, AgainAskNumberBottomSheetPageState, AgainAskNumberBottomSheetViewModel>(
        BottomSheetPhoneCertificationBinding::inflate
    ) {
    companion object {
        fun newInstance(
            phone : String,
            onSendButtonClick: () -> Unit
        ) = AgainAskNumberBottomSheet().apply {
            this.onSendButtonClick = onSendButtonClick
            this.phone = phone
        }
    }

    override val viewModel: AgainAskNumberBottomSheetViewModel by viewModels()

    private var onSendButtonClick: (() -> Unit)? = null
    private var phone : String = ""

    override fun initView() {
        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        viewModel.initView(phone)
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {

            }

            launch {
                viewModel.eventFlow.collect {
                    inspectEventFlow(it as AgainAskNumberBottomSheetEvent)
                }
            }
        }
    }


    private fun inspectEventFlow(event: AgainAskNumberBottomSheetEvent) {
        when (event) {
            AgainAskNumberBottomSheetEvent.ClickSendButton -> onButton()
            AgainAskNumberBottomSheetEvent.ClickCloseButton -> dismiss()
        }
    }

    private fun onButton() {
        onSendButtonClick?.let {
            it.invoke()
            dismiss()
        }
    }
}