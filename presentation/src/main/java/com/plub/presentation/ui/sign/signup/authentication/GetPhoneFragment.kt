package com.plub.presentation.ui.sign.signup.authentication

import android.os.CountDownTimer
import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.fragment.app.viewModels
import com.plub.domain.model.enums.SignUpPageType
import com.plub.domain.model.enums.ToastType
import com.plub.domain.model.vo.signUp.SignUpPageVo
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestFragment
import com.plub.presentation.databinding.FragmentGetPhoneBinding
import com.plub.presentation.ui.sign.signup.SignUpViewModel
import com.plub.presentation.ui.sign.signup.authentication.bottomSheet.AgainAskNumberBottomSheet
import com.plub.presentation.util.PlubLogger
import com.plub.presentation.util.PlubToast
import com.plub.presentation.util.TimeFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GetPhoneFragment : BaseTestFragment<FragmentGetPhoneBinding, GetPhonePageState, GetPhoneViewModel>(
    FragmentGetPhoneBinding::inflate
) {

    companion object{
        const val REMAINING_TIME = 300000L // 5분
        const val REDUCE_TIME = 1000L //1초
    }

    override val viewModel: GetPhoneViewModel by viewModels()
    private val parentViewModel: SignUpViewModel by viewModels({requireParentFragment()})
    private var remainingTimeInMillis = REMAINING_TIME
    private var countDownTimer: CountDownTimer? = null

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
                viewModel.eventFlow.collect{
                    inspectEventFlow(it as GetPhoneEvent)
                }
            }
        }
    }

    private fun inspectEventFlow(event: GetPhoneEvent) {
        when(event) {
            is GetPhoneEvent.ShowBottomSheet -> showAskBottomSheet()
            is GetPhoneEvent.CertificationSuccess -> {
                updateCertificationSuccessBackgroundColor()
                showSuccessToast()
            }
            is GetPhoneEvent.EditTextNorMalColor -> {
                updateCertificationNormalBackgroundColor()
            }
            is GetPhoneEvent.MoveToNext -> {
                parentViewModel.onMoveToNextPage(SignUpPageType.AUTHENTICATION)
            }
            is GetPhoneEvent.TimerStart -> {
                startTimer()
            }
        }
    }

    private fun showAskBottomSheet(){
        val bottomSheetAskAgain = AgainAskNumberBottomSheet.newInstance(
            binding.editTextInputPhone.text.toString()
        ){
            viewModel.onClickSendAgainButton()
        }
        bottomSheetAskAgain.show(
            parentFragmentManager,
            bottomSheetAskAgain.tag
        )
    }

    private fun updateCertificationSuccessBackgroundColor(){
        binding.editTextInputCertification.setBackgroundResource(R.drawable.bg_rectangle_empty_5f5ff9_radius_8)
    }

    private fun updateCertificationNormalBackgroundColor(){
        binding.editTextInputCertification.setBackgroundResource(R.drawable.bg_rectangle_empty_363636_radius_8)
    }

    private fun showSuccessToast(){
        context?.let {
            PlubToast.createToast(ToastType.COMPLETE,
                it, R.string.text_success_authentication).show()
        }
    }

    private fun startTimer() {
        remainingTimeInMillis = REMAINING_TIME
        countDownTimer = object : CountDownTimer(remainingTimeInMillis, REDUCE_TIME) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInMillis = millisUntilFinished
                binding.textViewTimer.text = TimeFormatter.convertToMMDotSSFormat(millisUntilFinished)
            }

            override fun onFinish() {

            }
        }.start()
    }
}