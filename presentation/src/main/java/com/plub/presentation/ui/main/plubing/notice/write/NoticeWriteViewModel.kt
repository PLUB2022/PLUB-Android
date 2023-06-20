package com.plub.presentation.ui.main.plubing.notice.write

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.viewModelScope
import com.plub.domain.model.enums.WriteType
import com.plub.domain.model.vo.notice.NoticeVo
import com.plub.domain.model.vo.notice.PostNoticeWriteRequestVo
import com.plub.domain.usecase.PostNoticeCreateUseCase
import com.plub.domain.usecase.PutNoticeEditUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.parcelableVo.ParseNoticeVo
import com.plub.presentation.util.PlubingInfo
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class NoticeWriteViewModel @Inject constructor(
    val postNoticeCreateUseCase: PostNoticeCreateUseCase,
    val putNoticeEditUseCase: PutNoticeEditUseCase,
    val resourceProvider: ResourceProvider,
) : BaseTestViewModel<NoticeWritePageState>() {

    companion object {
        private const val CONTENT_MAX_LENGTH = 500
    }

    private lateinit var writeType: WriteType
    private val plubingNameStateFlow: MutableStateFlow<String> = MutableStateFlow(PlubingInfo.info.name)
    private val titleStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val contentStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val contentCountStateFlow: MutableStateFlow<SpannableString> = MutableStateFlow(SpannableString(""))
    private val isPostButtonEnableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val contentMaxLengthStateFlow: MutableStateFlow<Int> = MutableStateFlow(CONTENT_MAX_LENGTH)

    override val uiState: NoticeWritePageState = NoticeWritePageState(
        plubingNameStateFlow.asStateFlow(),
        titleStateFlow,
        contentStateFlow,
        contentCountStateFlow.asStateFlow(),
        isPostButtonEnableStateFlow.asStateFlow(),
        contentMaxLengthStateFlow.asStateFlow()
    )

    private val plubingId = PlubingInfo.info.plubingId
    private var noticeId by Delegates.notNull<Int>()

    fun initArgs(args: NoticeWriteFragmentArgs) {
        this.writeType = args.writeType
        args.parseNoticeVo?.let {
            noticeId = it.noticeId
            initEditInfo(it)
        }
    }

    fun onTitleTextChanged() {
        updateIsPostButtonEnable(isPostButtonEnable())
    }

    fun onContentTextChanged() {
        val contentCount = getContentCountSpannableString(contentStateFlow.value.length)
        updateContentCount(contentCount)
        updateIsPostButtonEnable(isPostButtonEnable())
    }

    fun onClickPostUpload() {
        if(writeType == WriteType.CREATE) createNotice() else editNotice()
    }

    private fun initEditInfo(vo: ParseNoticeVo) {
        if(writeType != WriteType.EDIT) return

        updateTitle(vo.title)
        updateContent(vo.content)
        this.noticeId = vo.noticeId
        isPostButtonEnable()
    }

    private fun createNotice() {
        postCreateNotice {
            emitEventFlow(NoticeWriteEvent.CompleteCreate)
        }
    }

    private fun editNotice() {
        putEditNotice {
            val parseVo = ParseNoticeVo.mapToParse(it)
            emitEventFlow(NoticeWriteEvent.CompleteEdit(parseVo))
        }
    }

    private fun postCreateNotice(onSuccess: () -> Unit) {
        val request = PostNoticeWriteRequestVo(plubingId, title = titleStateFlow.value, content = contentStateFlow.value)
        viewModelScope.launch {
            postNoticeCreateUseCase(request).collect {
                inspectUiState(it, {
                    onSuccess()
                })
            }
        }
    }

    private fun putEditNotice(onSuccess: (NoticeVo) -> Unit) {
        val request = PostNoticeWriteRequestVo(plubingId, noticeId, titleStateFlow.value, contentStateFlow.value)
        viewModelScope.launch {
            putNoticeEditUseCase(request).collect {
                inspectUiState(it, onSuccess)
            }
        }
    }

    private fun isPostButtonEnable(): Boolean {
        val title = titleStateFlow.value
        val content = contentStateFlow.value
        return title.isNotEmpty() && content.isNotEmpty()
    }

    private fun getContentCountSpannableString(contentLength: Int): SpannableString {
        val contentCountString = resourceProvider.getString(R.string.word_middle_slash, contentLength, CONTENT_MAX_LENGTH)
        val contentCountColor = resourceProvider.getColor(R.color.color_363636)
        return SpannableString(contentCountString).apply {
            setSpan(ForegroundColorSpan(contentCountColor), 0, contentCountString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun updateContent(text: String) {
        viewModelScope.launch {
            contentStateFlow.update { text }
        }
    }

    private fun updateTitle(text: String) {
        viewModelScope.launch {
            titleStateFlow.update { text }
        }
    }

    private fun updateContentCount(text: SpannableString) {
        viewModelScope.launch {
            contentCountStateFlow.update { text }
        }
    }

    private fun updateIsPostButtonEnable(isEnable: Boolean) {
        viewModelScope.launch {
            isPostButtonEnableStateFlow.update { isEnable }
        }
    }

    fun goToBack(){
        emitEventFlow(NoticeWriteEvent.GoToBack)
    }
}
