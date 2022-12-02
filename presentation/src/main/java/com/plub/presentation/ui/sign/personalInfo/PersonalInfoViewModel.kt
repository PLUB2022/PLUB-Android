package com.plub.presentation.ui.sign.personalInfo

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.Gender
import com.plub.domain.model.state.PersonalInfoPageState
import com.plub.domain.model.vo.signUp.SignUpPageVo
import com.plub.domain.model.vo.signUp.personalInfo.PersonalInfoVo
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
) : BaseViewModel<PersonalInfoPageState>(PersonalInfoPageState()) {

    companion object {
        private const val LAST_YEAR_VALUE: Int = 1970
        private const val FIRST_DAY_VALUE: Int = 1
        private val MONTH_RANGE_LIST: List<Int> = (1..12).toList()
    }

    private val _moveToNextPage = MutableSharedFlow<PersonalInfoVo>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val moveToNextPage: SharedFlow<PersonalInfoVo> = _moveToNextPage.asSharedFlow()

    init {
        viewModelScope.launch {
            uiState.collect {
                verifyNextButtonEnable()
            }
        }
    }

    fun initSpinner() {
        val currentCalendar = Calendar.getInstance()
        val year = currentCalendar.get(Calendar.YEAR)
        val month = currentCalendar.get(Calendar.MONTH)
        val day = currentCalendar.get(Calendar.DAY_OF_MONTH)

        updateUiState { ui ->
            ui.copy(
                yearList = (LAST_YEAR_VALUE..year).toList(),
                monthList = MONTH_RANGE_LIST,
                dayList = (FIRST_DAY_VALUE..getCalculateLastDay(year, month)).toList(),
                defaultYear = year,
                defaultMonth = month + 1,
                defaultDay = day,
            )
        }
    }

    fun onInitPage(signUpPageVo: SignUpPageVo?) {
        (signUpPageVo as? PersonalInfoVo)?.let {
            updateUiState { ui ->
                ui.copy(
                    personalInfoVo = ui.personalInfoVo.copy(
                        gender = it.gender,
                        year = it.year,
                        month = it.month,
                        day = it.day,
                    )
                )
            }
        }
    }

    fun onClickGender(gender: Gender) {
        updateUiState { ui ->
            ui.copy(
                personalInfoVo = ui.personalInfoVo.copy(
                    gender = gender
                )
            )
        }
    }

    fun onClickNextButton() {
        viewModelScope.launch {
            _moveToNextPage.emit(uiState.value.personalInfoVo)
        }
    }

    private fun verifyNextButtonEnable() {
        updateUiState {
            it.copy(
                isNextButtonEnable = isNextButtonEnable(it.personalInfoVo)
            )
        }
    }

    private fun isNextButtonEnable(personalInfoVo: PersonalInfoVo): Boolean {
        return personalInfoVo.run {
            gender != null && year != 0 && month != 0 && day != 0
        }
    }

    private fun getCalculateLastDay(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }
}