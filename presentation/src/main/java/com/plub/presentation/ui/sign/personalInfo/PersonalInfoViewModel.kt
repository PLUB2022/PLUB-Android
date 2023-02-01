package com.plub.presentation.ui.sign.personalInfo

import com.plub.domain.model.enums.Gender
import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
) : BaseViewModel<PersonalInfoPageState>(PersonalInfoPageState()) {

    companion object {
        private const val DATE_FORMAT = "yyyy년 MM월 dd일"
    }

    fun onInitPersonalInfoVo(personalInfoVo: PersonalInfoVo) {
        if(uiState.value != PersonalInfoPageState()) return
        val birthString = personalInfoVo.calendar?.let { getBirthString(it) } ?: ""
        updateUiState { ui ->
            ui.copy(
                personalInfoVo = personalInfoVo,
                isNextButtonEnable = isNextButtonEnable(personalInfoVo),
                birthString = birthString,
                birthIsActive = birthIsActive(birthString)
            )
        }
    }

    fun onClickGender(gender: Gender) {
        updateUiState { ui ->
            val newPersonalInfo = ui.personalInfoVo.copy(
                gender = gender
            )
            ui.copy(
                personalInfoVo = newPersonalInfo,
                isNextButtonEnable = isNextButtonEnable(newPersonalInfo)
            )
        }
    }

    fun onClickBirthImage() {
        val calendar = uiState.value.personalInfoVo.calendar ?: Calendar.getInstance()
        emitEventFlow(PersonalInfoEvent.ShowDatePickerDialog(calendar))
    }

    fun onClickNextButton() {
        emitEventFlow(PersonalInfoEvent.MoveToNext(uiState.value.personalInfoVo))
    }

    fun onClickOkDatePickerDialog(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val birthString = getBirthString(calendar)

        updateUiState { ui ->
            val newPersonalInfo = ui.personalInfoVo.copy(
                calendar = calendar
            )
            ui.copy(
                personalInfoVo = newPersonalInfo,
                isNextButtonEnable = isNextButtonEnable(newPersonalInfo),
                birthString = birthString,
                birthIsActive = birthIsActive(birthString)
            )
        }
    }

    private fun isNextButtonEnable(personalInfoVo: PersonalInfoVo): Boolean {
        return personalInfoVo.run {
            gender != null && calendar != null
        }
    }

    private fun getBirthString(calendar: Calendar):String {
        val date = calendar.time
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun birthIsActive(birthString:String):Boolean {
        return birthString.isNotEmpty()
    }
}