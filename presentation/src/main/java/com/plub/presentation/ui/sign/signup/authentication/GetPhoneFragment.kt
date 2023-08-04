package com.plub.presentation.ui.sign.signup.authentication

import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.fragment.app.viewModels
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentGetPhoneBinding
import com.plub.presentation.ui.sign.signup.SignUpViewModel
import com.plub.presentation.util.PlubLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GetPhoneFragment : BaseTestFragment<FragmentGetPhoneBinding, GetPhonePageState, GetPhoneViewModel>(
    FragmentGetPhoneBinding::inflate
) {

    override val viewModel: GetPhoneViewModel by viewModels()
    private val parentViewModel: SignUpViewModel by viewModels({requireParentFragment()})

    override fun initView() {
        binding.apply {
            vm = viewModel
            editTextInputPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {

            launch {

            }

            launch {
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as GetPhoneEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: GetPhoneEvent) {
        when(event) {
            is GetPhoneEvent.MoveToEnd -> {
                focusToEnd()
            }
        }
    }

    private fun focusToEnd(){
        binding.apply {
            editTextInputPhone.setSelection(editTextInputPhone.length())
        }
    }
}