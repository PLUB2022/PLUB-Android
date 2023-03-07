package com.plub.presentation.ui.main.plubing

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.PlubingMainPageType
import com.plub.domain.model.vo.plub.PlubingMainVo
import com.plub.domain.usecase.FetchPlubingMainUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.PlubingInfo
import com.plub.presentation.util.ResourceProvider
import com.plub.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs
import kotlin.properties.Delegates

@HiltViewModel
class PlubingMainViewModel @Inject constructor(
    val fetchPlubingMainUseCase: FetchPlubingMainUseCase,
    val resourceProvider: ResourceProvider,
) : BaseViewModel<PlubingMainPageState>(PlubingMainPageState()) {

    companion object {
        private const val SEPARATOR_OF_DAY = ","
        private const val SEPARATOR_OF_LOCATE = " "
    }

    private var plubingId by Delegates.notNull<Int>()

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

    fun initPlubingId(id:Int) {
        plubingId = id
    }

    fun onFetchPlubingMainInfo() {
        viewModelScope.launch {
            fetchPlubingMainUseCase(plubingId).collect {
                inspectUiState(it, ::onSuccessPlubingMainInfo)
            }
        }
    }

    fun onChangedPage(position: Int) {
        val pageType = PlubingMainPageType.valueOf(position)
        updateUiState { uiState ->
            uiState.copy(
                pageType = pageType
            )
        }
    }

    fun onClickWrite() {
        val plubingName = uiState.value.plubingName
        when(uiState.value.pageType) {
            PlubingMainPageType.BOARD -> emitEventFlow(PlubingMainEvent.GoToWriteBoard)
            PlubingMainPageType.TODO_LIST -> Unit
        }
    }

    fun onClickProfile(profileId: Int) {

    }

    private fun onSuccessPlubingMainInfo(mainVo: PlubingMainVo) {
        PlubingInfo.updateInfo(mainVo)
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
