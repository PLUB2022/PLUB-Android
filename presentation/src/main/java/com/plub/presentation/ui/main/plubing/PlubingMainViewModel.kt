package com.plub.presentation.ui.main.plubing

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.plub.PlubingMainVo
import com.plub.domain.usecase.FetchPlubingMainUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.ResourceProvider
import com.plub.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class PlubingMainViewModel @Inject constructor(
    val fetchPlubingMainUseCase: FetchPlubingMainUseCase,
    val resourceProvider: ResourceProvider,
) : BaseViewModel<PlubingMainPageState>(PlubingMainPageState()) {

    companion object {
        private const val SEPARATOR_OF_DAY = ","
        private const val SEPARATOR_OF_LOCATE = " "
    }

    fun onAppBarOffsetChanged(height: Float, totalScrollRange: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            val offsetAlpha: Float = height / totalScrollRange
            val headerAlpha = abs(offsetAlpha)
            updateUiState {
                it.copy(
                    headerAlpha = headerAlpha
                )
            }
        }
    }

    fun onFetchPlubingMainInfo(id: Int) {
        viewModelScope.launch {
            fetchPlubingMainUseCase(id).collect {
                inspectUiState(it, ::onSuccessPlubingMainInfo)
            }
        }
    }

    fun onClickProfile(id: Int) {

    }

    private fun onSuccessPlubingMainInfo(mainVo: PlubingMainVo) {
        mainVo.run {
            val days = days.joinToString(SEPARATOR_OF_DAY)
            val time = TimeFormatter.getAmPmHourMin(time)
            val date = getStringResource(R.string.word_middle_line, days, time)
            val location = listOf(address, roadAddress, placeName).joinToString(SEPARATOR_OF_LOCATE)
            updateUiState {
                it.copy(
                    plubingName = name,
                    plubingDate = date,
                    plubingLocation = location,
                    plubingMainImage = mainImage,
                    memberList = memberInfoList
                )
            }
        }
    }

    private fun getStringResource(res: Int, vararg formatArgs: Any?): String {
        return resourceProvider.getString(res, *formatArgs)
    }
}
