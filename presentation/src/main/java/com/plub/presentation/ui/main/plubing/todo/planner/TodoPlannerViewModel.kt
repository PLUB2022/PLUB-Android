package com.plub.presentation.ui.main.plubing.todo.planner

import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.DialogMenuType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.todo.GetMyTodoListInDayRequestVo
import com.plub.domain.model.vo.todo.GetTodoDaysRequestVo
import com.plub.domain.model.vo.todo.TodoItemVo
import com.plub.domain.model.vo.todo.TodoProofRequestVo
import com.plub.domain.model.vo.todo.TodoRequestVo
import com.plub.domain.model.vo.todo.TodoWriteRequestVo
import com.plub.domain.usecase.DeleteTodoUseCase
import com.plub.domain.usecase.GetMyTodoListInDayUseCase
import com.plub.domain.usecase.GetTodoDaysInMonthUseCase
import com.plub.domain.usecase.PostTodoCreateUseCase
import com.plub.domain.usecase.PostTodoProofUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.domain.usecase.PutTodoCancelUseCase
import com.plub.domain.usecase.PutTodoCompleteUseCase
import com.plub.domain.usecase.PutTodoEditUseCase
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.parcelableVo.ParseTodoItemVo
import com.plub.presentation.util.PlubingInfo
import com.plub.presentation.util.ResourceProvider
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.format.DateTimeFormatter
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class TodoPlannerViewModel @Inject constructor(
    val getMyTodoListInDayUseCase: GetMyTodoListInDayUseCase,
    val getTodoDaysInMonthUseCase: GetTodoDaysInMonthUseCase,
    val postTodoCreateUseCase: PostTodoCreateUseCase,
    val putTodoEditUseCase: PutTodoEditUseCase,
    val deleteTodoUseCase: DeleteTodoUseCase,
    val postTodoProofUseCase: PostTodoProofUseCase,
    val postUploadFileUseCase: PostUploadFileUseCase,
    val putTodoCompleteUseCase: PutTodoCompleteUseCase,
    val putTodoCancelUseCase: PutTodoCancelUseCase,
    val resourceProvider: ResourceProvider,
) : BaseTestViewModel<TodoPlannerPageState>() {

    companion object {
        private const val DATE_FORMAT_CALENDAR_OTHER_YEAR = "YYYY년 MM월"
        private const val DATE_FORMAT_CALENDAR_THIS_YEAR = "MM월"
        private const val DATE_FORMAT_SERVER_FORM_DATE = "YYYY-MM-dd"
        private const val DATE_FORMAT_TODO_DATE = "MM.dd"
        private const val SPLIT_DATE = "-"
        private const val FIRST_INDEX = 0
    }

    private var plubingId = PlubingInfo.info.plubingId
    private var currentDate: String? = null
    private var editTodoId: Int? = null

    private val plubNameStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val todoDaysStateFlow: MutableStateFlow<List<Int>> = MutableStateFlow(emptyList())
    private val todoListStateFlow: MutableStateFlow<List<TodoItemVo>> = MutableStateFlow(emptyList())
    private val calendarDateTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val todoDateTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val isTodayStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isTodoEditModeStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val todoTextStateFlow: MutableStateFlow<String> = MutableStateFlow("")

    override val uiState: TodoPlannerPageState = TodoPlannerPageState(
        plubNameStateFlow.asStateFlow(),
        todoDaysStateFlow.asStateFlow(),
        todoListStateFlow.asStateFlow(),
        calendarDateTextStateFlow.asStateFlow(),
        todoDateTextStateFlow.asStateFlow(),
        isTodayStateFlow.asStateFlow(),
        isTodoEditModeStateFlow.asStateFlow(),
        todoTextStateFlow.asStateFlow()
    )

    fun initDate(date:String) {
        val todoDate = if(date.isEmpty()) CalendarDay.today() else {
            val dateList = date.split(SPLIT_DATE)
            CalendarDay.from(dateList[0].toInt(), dateList[1].toInt(), dateList[2].toInt())
        }
        onChangeCalendarMonth(todoDate)
        onSelectedCalendarDay(todoDate)
        emitEventFlow(TodoPlannerEvent.SetCalendarCurrentDate(todoDate))
    }

    fun onSelectedCalendarDay(date: CalendarDay) {
        clear()
        currentDate = DateTimeFormatter.ofPattern(DATE_FORMAT_SERVER_FORM_DATE).format(date.date).also {
            getMyTodoListInDay(it)
        }

        val todoDateString = DateTimeFormatter.ofPattern(DATE_FORMAT_TODO_DATE).format(date.date)
        updateTodoDateText(todoDateString)

        val isToday = date == CalendarDay.today()
        updateIsToday(isToday)
    }

    fun onUpdatePlubName() {
        updatePlubName()
    }

    fun onClickProofComplete(todoId: Int, proofFile: File) {
        postUploadImage(proofFile) {
            postTodoProof(todoId, it) {
                val proofedList = getTodoListProofChanged(todoId)
                updateTodoList(proofedList)
            }
        }
    }

    fun onClickTodoCheck(vo: TodoItemVo) {
        if (vo.isChecked) cancelTodoCheck(vo) else completeTodoCheck(vo)
    }

    fun onClickTodoMenu(vo: TodoItemVo) {
        val menuType = when {
            vo.isChecked && vo.isProof -> DialogMenuType.TODO_PLANNER_CHECKED_PROOFED_TYPE
            vo.isChecked && !vo.isProof -> DialogMenuType.TODO_PLANNER_CHECKED_NOT_PROOFED_TYPE
            else -> DialogMenuType.TODO_PLANNER_UNCHECKED_TYPE
        }
        emitEventFlow(TodoPlannerEvent.ShowMenuBottomSheetDialog(menuType, vo))
    }

    fun onClickMenuItemType(item: DialogMenuItemType, todoItemVo: TodoItemVo) {
        when (item) {
            DialogMenuItemType.TODO_EDIT -> todoEditMode(todoItemVo)
            DialogMenuItemType.TODO_DELETE -> todoDelete(todoItemVo.todoId)
            DialogMenuItemType.TODO_PROOF -> showProofDialog(todoItemVo)
            else -> Unit
        }
    }

    fun onChangeCalendarMonth(date: CalendarDay) {
        val title = getCalendarTitleFormat(date)
        updateCalendarDateText(title)
        getTodoDaysInMonth(date.year, date.month)
    }

    fun onClickPreviousMonth() {
        emitEventFlow(TodoPlannerEvent.CalendarMonthPrevious)
    }

    fun onClickNextMonth() {
        emitEventFlow(TodoPlannerEvent.CalendarMonthNext)
    }

    fun onClickTodoWrite(content: String) {
        if (content.isBlank()) return

        val editTodoId = editTodoId
        if(editTodoId == null) todoCreate(content) else todoEdit(editTodoId, content)
    }

    private fun getTodoDaysInMonth(year: Int, month: Int) {
        viewModelScope.launch {
            val request = GetTodoDaysRequestVo(plubingId, year, month)
            getTodoDaysInMonthUseCase(request).collect {
                inspectUiState(it, { list ->
                    updateTodoDays(list)
                })
            }
        }
    }

    private fun getMyTodoListInDay(date: String) {
        val request = GetMyTodoListInDayRequestVo(plubingId, date)
        viewModelScope.launch {
            getMyTodoListInDayUseCase(request).collect {
                inspectUiState(it, { vo ->
                    updateTodoList(vo.todoList)
                })
            }
        }
    }

    private fun todoDelete(todoId: Int) {
        val request = TodoRequestVo(plubingId, todoId = todoId)
        viewModelScope.launch {
            deleteTodoUseCase(request).collect {
                inspectUiState(it, { vo ->
                    val deletedList = uiState.todoList.value.filterNot { it.todoId == todoId }
                    updateTodoList(deletedList)
                })
            }
        }
    }

    private fun todoCreate(content: String) {
        currentDate?.let { date ->
            val request = TodoWriteRequestVo(plubbingId = plubingId, content = content, date = date)
            viewModelScope.launch {
                postTodoCreateUseCase(request).collect {
                    inspectUiState(it, { vo ->
                        val newList = uiState.todoList.value.toMutableList()
                        newList.add(vo)
                        updateTodoList(newList)
                        clear()
                    })
                }
            }
        }
    }

    private fun todoEdit(todoId:Int, content: String) {
        currentDate?.let { date ->
            val request = TodoWriteRequestVo(plubingId, todoId, content, date)
            viewModelScope.launch {
                putTodoEditUseCase(request).collect {
                    inspectUiState(it, { vo ->
                        val newList = uiState.todoList.value.toMutableList()
                        val idx = newList.indexOfFirst { it.todoId == vo.todoId }
                        newList[idx] = vo
                        updateTodoList(newList)
                        clear()
                    })
                }
            }
        }
    }

    private fun postUploadImage(imageFile: File, onSuccess: (String) -> Unit) {
        val fileRequest = UploadFileRequestVo(UploadFileType.PLUBING_TODO, imageFile)
        viewModelScope.launch {
            postUploadFileUseCase(fileRequest).collect { state ->
                inspectUiState(state, { vo ->
                    onSuccess(vo.fileUrl)
                })
            }
        }
    }

    private fun postTodoProof(todoId: Int, imageUrl: String, onSuccess: () -> Unit) {
        val request = TodoProofRequestVo(plubingId, todoId, imageUrl)
        viewModelScope.launch {
            postTodoProofUseCase(request).collect { state ->
                inspectUiState(state, {
                    onSuccess()
                })
            }
        }
    }

    private fun getTodoComplete(todoId: Int, onSuccess: () -> Unit) {
        val request = TodoRequestVo(plubingId, todoId = todoId)
        viewModelScope.launch {
            putTodoCompleteUseCase(request).collect { state ->
                inspectUiState(state, {
                    onSuccess()
                })
            }
        }
    }

    private fun putTodoCancel(todoId: Int, onSuccess: () -> Unit) {
        val request = TodoRequestVo(plubingId, todoId = todoId)
        viewModelScope.launch {
            putTodoCancelUseCase(request).collect { state ->
                inspectUiState(state, {
                    onSuccess()
                })
            }
        }
    }

    private fun updateTodoList(list: List<TodoItemVo>) {
        viewModelScope.launch {
            todoListStateFlow.update { list }
        }
    }

    private fun updateTodoDays(list: List<Int>) {
        viewModelScope.launch {
            todoDaysStateFlow.update { list }
        }
    }

    private fun updatePlubName() {
        viewModelScope.launch {
            plubNameStateFlow.update { PlubingInfo.info.name }
        }
    }

    private fun updateCalendarDateText(dateText: String) {
        viewModelScope.launch {
            calendarDateTextStateFlow.update { dateText }
        }
    }

    private fun updateTodoDateText(dateText: String) {
        viewModelScope.launch {
            todoDateTextStateFlow.update { dateText }
        }
    }

    private fun updateIsToday(isToday: Boolean) {
        viewModelScope.launch {
            isTodayStateFlow.update { isToday }
        }
    }

    private fun updateIsTodoEditMode(isEditMode: Boolean) {
        viewModelScope.launch {
            isTodoEditModeStateFlow.update { isEditMode }
        }
    }

    private fun updateTodoText(text: String) {
        viewModelScope.launch {
            todoTextStateFlow.update { text }
        }
    }

    private fun getCalendarTitleFormat(calendarDay: CalendarDay): String {
        val format = if (isThisYear(calendarDay.year)) DATE_FORMAT_CALENDAR_THIS_YEAR else DATE_FORMAT_CALENDAR_OTHER_YEAR
        return DateTimeFormatter.ofPattern(format).format(calendarDay.date)
    }

    private fun isThisYear(year: Int): Boolean {
        val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        return year == currentYear
    }

    private fun showProofDialog(todoItemVo: TodoItemVo) {
        val vo = ParseTodoItemVo.mapToParse(todoItemVo)
        emitEventFlow(TodoPlannerEvent.ShowTodoProofDialog(vo))
    }

    private fun cancelTodoCheck(vo: TodoItemVo) {
        putTodoCancel(vo.todoId) {
            checkChangeRebaseUpdate(vo, false)
        }
    }

    private fun completeTodoCheck(vo: TodoItemVo) {
        getTodoComplete(vo.todoId) {
            checkChangeRebaseUpdate(vo, true)
            showProofDialog(vo)
        }
    }

    private fun checkChangeRebaseUpdate(vo: TodoItemVo, isTop: Boolean) {
        val checkChangedVo = vo.copy(isChecked = !vo.isChecked)
        val rebasedList = getTodoListRebaseItem(checkChangedVo, isTop)
        updateTodoList(rebasedList)
    }

    private fun getTodoListRebaseItem(vo: TodoItemVo, isTop: Boolean): List<TodoItemVo> {
        return uiState.todoList.value.toMutableList().apply {
            val removePosition = indexOfFirst { it.todoId == vo.todoId }
            removeAt(removePosition)
            if(isTop) add(FIRST_INDEX, vo) else add(vo)
        }
    }

    private fun getTodoListProofChanged(todoId: Int): List<TodoItemVo> {
        return uiState.todoList.value.map {
            val isProofed = if (it.todoId == todoId) !it.isProof else it.isProof
            it.copy(isProof = isProofed)
        }
    }

    private fun todoEditMode(todoItemVo: TodoItemVo) {
        emitEventFlow(TodoPlannerEvent.ShowKeyboard)
        editTodoId = todoItemVo.todoId
        updateTodoText(todoItemVo.content)
        updateIsTodoEditMode(true)
    }

    private fun clear() {
        editTodoId = null
        updateIsTodoEditMode(false)
        emitEventFlow(TodoPlannerEvent.ClearTodoEditText)
        emitEventFlow(TodoPlannerEvent.HideKeyboard)
    }
}
