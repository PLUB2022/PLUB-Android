package com.plub.presentation.ui.sign.personalInfo

import android.app.DatePickerDialog
import androidx.fragment.app.viewModels
import com.plub.domain.model.enums.SignUpPageType
import com.plub.domain.model.state.PersonalInfoPageState
import com.plub.presentation.R
import com.plub.presentation.base.BaseFragment
import com.plub.presentation.databinding.FragmentPersonalInfoBinding
import com.plub.presentation.ui.sign.signup.SignUpFragment
import com.plub.presentation.ui.sign.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class PersonalInfoFragment : BaseFragment<FragmentPersonalInfoBinding, PersonalInfoPageState, PersonalInfoViewModel>(
    FragmentPersonalInfoBinding::inflate
) {

    override val viewModel: PersonalInfoViewModel by viewModels()
    private val parentViewModel: SignUpViewModel by viewModels({requireParentFragment()})

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initState() {
        super.initState()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.moveToNextPage.collect {
                    parentViewModel.onMoveToNextPage(SignUpPageType.PERSONAL_INFO, it)
                }
            }

            launch {
                parentViewModel.uiState.collect {
                    viewModel.onInitPersonalInfoVo(it.personalInfoVo)
                }
            }

            launch {
                viewModel.showDatePickerDialog.collect {
                    showDialogDatePicker(it)
                }
            }
        }
    }

    private fun showDialogDatePicker(calendar: Calendar) {
        val datePickerDialog = DatePickerDialog(requireContext(),R.style.PlubDatePickerStyle,
            { datePicker, y, m, d ->
                viewModel.onClickOkDatePickerDialog(y,m,d)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}