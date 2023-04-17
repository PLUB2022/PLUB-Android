package com.plub.presentation.ui.main.home.recruitment


import androidx.lifecycle.viewModelScope
import com.plub.domain.model.vo.bookmark.PlubBookmarkResponseVo
import com.plub.domain.model.vo.home.applicantsRecruitVo.ApplicantsRecruitRequestVo
import com.plub.domain.model.vo.home.applyVo.QuestionsResponseVo
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailJoinedAccountsVo
import com.plub.domain.model.vo.home.recruitDetailVo.RecruitDetailResponseVo
import com.plub.domain.usecase.*
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruitmentViewModel @Inject constructor(
    val getRecruitDetailUseCase: GetRecruitDetailUseCase,
    val postBookmarkPlubRecruitUseCase: PostBookmarkPlubRecruitUseCase,
    val deleteMyApplicationUseCase: DeleteMyApplicationUseCase,
    val getRecruitQuestionUseCase: GetRecruitQuestionUseCase,
    val postApplyRecruitUseCase: PostApplyRecruitUseCase,
    val putEndRecruitUseCase: PutEndRecruitUseCase
) : BaseTestViewModel<RecruitmentPageState>() {

    companion object{
        const val SEPARATOR_OF_DAY = ", "
    }

    private var plubbingId : Int = 0
    private val recruitTitleStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val recruitIntroduceStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val categoriesStateFlow : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    private val plubbingNameStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val plubbingGoalStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val plubbingMainImageStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val plubbingDaysStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val placeNameStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val accountNumStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val plubbingTimeStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val isBookmarkedStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isAppliedStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isHostStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val joinedAccountsStateFlow : MutableStateFlow<List<RecruitDetailJoinedAccountsVo>> = MutableStateFlow(emptyList())
    override val uiState: RecruitmentPageState = RecruitmentPageState(
        recruitTitle = recruitTitleStateFlow.asStateFlow(),
        recruitIntroduce = recruitIntroduceStateFlow.asStateFlow(),
        categories = categoriesStateFlow.asStateFlow(),
        plubbingName = plubbingNameStateFlow.asStateFlow(),
        plubbingGoal = plubbingGoalStateFlow.asStateFlow(),
        plubbingMainImage = plubbingMainImageStateFlow.asStateFlow(),
        plubbingDays = plubbingDaysStateFlow.asStateFlow(),
        placeName = placeNameStateFlow.asStateFlow(),
        accountNum = accountNumStateFlow.asStateFlow(),
        plubbingTime = plubbingTimeStateFlow.asStateFlow(),
        isBookmarked = isBookmarkedStateFlow.asStateFlow(),
        isApplied = isAppliedStateFlow.asStateFlow(),
        isHost = isHostStateFlow.asStateFlow(),
        joinedAccounts = joinedAccountsStateFlow.asStateFlow()

    )

    fun fetchRecruitmentDetail(Id : Int){
        plubbingId = Id
        viewModelScope.launch {
            getRecruitDetailUseCase(plubbingId).collect{ state ->
                inspectUiState(state, ::handleSuccessGetRecruitDetail)
            }
        }
    }

    private fun handleSuccessGetRecruitDetail(data : RecruitDetailResponseVo){
        val days = data.plubbingDays.joinToString(SEPARATOR_OF_DAY)
        val time = TimeFormatter.getAmPmHourMin(data.plubbingTime)
        viewModelScope.launch {
            recruitTitleStateFlow.update { data.recruitTitle }
            recruitIntroduceStateFlow.update { data.recruitIntroduce }
            categoriesStateFlow.update { data.categories }
            plubbingNameStateFlow.update { data.plubbingName }
            plubbingGoalStateFlow.update { data.plubbingGoal }
            plubbingMainImageStateFlow.update { data.plubbingMainImage }
            plubbingDaysStateFlow.update { days }
            placeNameStateFlow.update { data.placeName }
            accountNumStateFlow.update { (data.remainAccountNum + data.curAccountNum).toString() }
            plubbingTimeStateFlow.update { time }
            isBookmarkedStateFlow.update { data.isBookmarked }
            isAppliedStateFlow.update { data.isApplied }
            isHostStateFlow.update { data.isHost }
            joinedAccountsStateFlow.update { data.joinedAccounts }
        }
    }

    fun clickBookmark(){
        viewModelScope.launch{
            postBookmarkPlubRecruitUseCase(plubbingId).collect{
                inspectUiState(it, ::successBookMarkChange)
            }
        }
    }

    private fun successBookMarkChange(data : PlubBookmarkResponseVo){
        viewModelScope.launch{
            isBookmarkedStateFlow.update { data.isBookmarked }
        }
    }

    fun goToApplyPlubbing(){
        if(uiState.isApplied.value) emitEventFlow(RecruitEvent.CancelApply)
        else getPlubbingQuestion()
    }

    private fun getPlubbingQuestion(){
        viewModelScope.launch{
            getRecruitQuestionUseCase(plubbingId).collect{
                inspectUiState(it, ::onSuccessGetQuestions)
            }
        }
    }

    private fun onSuccessGetQuestions(vo : QuestionsResponseVo){
        if(vo.questions.isEmpty()) applyPlubbing()
        else emitEventFlow(RecruitEvent.GoToApplyPlubbingFragment)
    }

    private fun applyPlubbing(){
        val request = ApplicantsRecruitRequestVo(plubbingId, emptyList())
        viewModelScope.launch {
            postApplyRecruitUseCase(request).collect{
                inspectUiState(it, { onSuccessApplyRecruit() })
            }
        }
    }

    private fun onSuccessApplyRecruit(){
        emitEventFlow(RecruitEvent.ShowDialog)
    }

    fun goToBack(){
        emitEventFlow(RecruitEvent.GoToBack)
    }

    fun goToProfile(accountId : Int, nickname : String){
        emitEventFlow(RecruitEvent.GoToProfileFragment(accountId, nickname))
    }

    fun openBottomSheet(){
        emitEventFlow(RecruitEvent.OpenBottomSheet(uiState.joinedAccounts.value))
    }

    fun onClickReport(){
        emitEventFlow(RecruitEvent.GoToReport)
    }

    fun cancelApply(){
        viewModelScope.launch {
            deleteMyApplicationUseCase(plubbingId).collect{
                inspectUiState(it, { goToBack() })
            }
        }
    }

    fun goToEditPage(){
        emitEventFlow(RecruitEvent.GoToEditFragment)
    }

    fun seeApplicants(){
        emitEventFlow(RecruitEvent.GoToSeeApplicants)
    }

    fun endRecruit(){
        viewModelScope.launch {
            putEndRecruitUseCase(plubbingId).collect{ state ->
                inspectUiState(state, {handleSuccessEndRecruit()})
            }
        }
    }

    private fun handleSuccessEndRecruit(){
        emitEventFlow(RecruitEvent.GoToBack)
    }
}