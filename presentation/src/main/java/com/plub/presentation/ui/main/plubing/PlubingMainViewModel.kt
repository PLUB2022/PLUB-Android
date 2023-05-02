package com.plub.presentation.ui.main.plubing

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.PlubingMainPageType
import com.plub.domain.model.vo.plub.PlubingMainVo
import com.plub.domain.model.vo.plub.PlubingMemberInfoVo
import com.plub.domain.usecase.FetchPlubingMainUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.util.PlubingInfo
import com.plub.presentation.util.ResourceProvider
import com.plub.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs
import kotlin.properties.Delegates

@HiltViewModel
class PlubingMainViewModel @Inject constructor(
    val fetchPlubingMainUseCase: FetchPlubingMainUseCase,
    val resourceProvider: ResourceProvider,
) : BaseTestViewModel<PlubingMainPageState>() {

    companion object {
        private const val SEPARATOR_OF_DAY = ","
        private const val SEPARATOR_OF_LOCATE = " "
    }

    private var plubingId by Delegates.notNull<Int>()

    private val headerAlphaStateFlow: MutableStateFlow<Float> = MutableStateFlow(1f)
    private val plubingNameStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val plubingDateStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val plubingLocationStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val plubingMainImageStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val pageTypeStateFlow: MutableStateFlow<PlubingMainPageType> = MutableStateFlow(PlubingMainPageType.BOARD)
    private val memberListStateFlow: MutableStateFlow<List<PlubingMemberInfoVo>> = MutableStateFlow(emptyList())

    override val uiState: PlubingMainPageState = PlubingMainPageState(
        headerAlphaStateFlow.asStateFlow(),
        plubingNameStateFlow.asStateFlow(),
        plubingDateStateFlow.asStateFlow(),
        plubingLocationStateFlow.asStateFlow(),
        plubingMainImageStateFlow.asStateFlow(),
        pageTypeStateFlow.asStateFlow(),
        memberListStateFlow.asStateFlow(),
    )

    fun onAppBarOffsetChanged(height: Float, totalScrollRange: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            val offsetAlpha: Float = height / totalScrollRange
            val headerAlpha = abs(offsetAlpha)
            updateHeaderAlpha(headerAlpha)
        }
    }

    fun initPlubingId(id:Int) {
        plubingId = id
    }

    fun onFetchPlubingMainInfo() {
        if(memberListStateFlow.value.isNotEmpty()) return
        viewModelScope.launch {
            fetchPlubingMainUseCase(plubingId).collect {
                inspectUiState(it, ::onSuccessPlubingMainInfo)
            }
        }
    }

    fun onChangedPage(position: Int) {
        val pageType = PlubingMainPageType.valueOf(position)
        updatePageType(pageType)
    }

    fun onClickWrite() {
        when(pageTypeStateFlow.value) {
            PlubingMainPageType.BOARD -> emitEventFlow(PlubingMainEvent.GoToWriteBoard)
            PlubingMainPageType.TODO_LIST -> emitEventFlow(PlubingMainEvent.GoToPlannerTodo)
        }
    }

    fun goToSchedule() {
        emitEventFlow(
            PlubingMainEvent.GoToSchedule(plubingId, uiState.plubingName.value)
        )
    }

    fun onClickProfile(profileId: Int) {

    }

    fun onClickNotice() {
        emitEventFlow(PlubingMainEvent.GoToNotice)
    }

    private fun onSuccessPlubingMainInfo(mainVo: PlubingMainVo) {
        PlubingInfo.updateInfo(mainVo)
        mainVo.run {
            val days = days.joinToString(SEPARATOR_OF_DAY)
            val time = TimeFormatter.getAmPmHourMin(time)
            val date = getStringResource(R.string.word_middle_line, days, time)
            val location = listOf(address, roadAddress, placeName).joinToString(SEPARATOR_OF_LOCATE)
            updatePlubingName(name)
            updatePlubingDate(date)
            updatePlubingLocation(location)
            updatePlubingMainImage(mainImage)
            updatePlubingMemberList(memberInfoList)
        }
    }

    private fun getStringResource(res: Int, vararg formatArgs: Any?): String {
        return resourceProvider.getString(res, *formatArgs)
    }

    private fun updateHeaderAlpha(alpha: Float) {
        viewModelScope.launch {
            headerAlphaStateFlow.update { alpha }
        }
    }

    private fun updatePageType(type: PlubingMainPageType) {
        viewModelScope.launch {
            pageTypeStateFlow.update { type }
        }
    }

    private fun updatePlubingName(name: String) {
        viewModelScope.launch {
            plubingNameStateFlow.update { name }
        }
    }
    private fun updatePlubingDate(date: String) {
        viewModelScope.launch {
            plubingDateStateFlow.update { date }
        }
    }
    private fun updatePlubingLocation(location: String) {
        viewModelScope.launch {
            plubingLocationStateFlow.update { location }
        }
    }
    private fun updatePlubingMainImage(image: String) {
        viewModelScope.launch {
            plubingMainImageStateFlow.update { image }
        }
    }

    private fun updatePlubingMemberList(list: List<PlubingMemberInfoVo>) {
        viewModelScope.launch {
            memberListStateFlow.update { list }
        }
    }

    fun goToBack(){
        emitEventFlow(PlubingMainEvent.GoToBack)
    }

    fun goToArchive(){
        emitEventFlow(PlubingMainEvent.GoToArchive)
    }
}
