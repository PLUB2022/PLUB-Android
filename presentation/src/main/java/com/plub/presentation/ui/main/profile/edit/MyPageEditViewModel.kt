package com.plub.presentation.ui.main.profile.edit

import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.viewModelScope
import com.canhub.cropper.CropImageView
import com.plub.domain.error.AccountError
import com.plub.domain.error.ImageError
import com.plub.domain.error.NicknameError
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.enums.UploadFileType
import com.plub.domain.model.vo.account.MyInfoResponseVo
import com.plub.domain.model.vo.account.UpdateMyInfoRequestVo
import com.plub.domain.model.vo.media.ChangeFileRequestVo
import com.plub.domain.model.vo.media.UploadFileRequestVo
import com.plub.domain.model.vo.media.UploadFileResponseVo
import com.plub.domain.model.vo.signUp.profile.NicknameCheckRequestVo
import com.plub.domain.usecase.GetNicknameCheckUseCase
import com.plub.domain.usecase.PostChangeFileUseCase
import com.plub.domain.usecase.PostUpdateMyInfoUseCase
import com.plub.domain.usecase.PostUploadFileUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseTestViewModel
import com.plub.presentation.ui.main.archive.ArchiveEvent
import com.plub.presentation.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MyPageEditViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val imageUtil: ImageUtil,
    val getNicknameCheckUseCase: GetNicknameCheckUseCase,
    val postUploadFileUseCase: PostUploadFileUseCase,
    val postChangeFileUseCase: PostChangeFileUseCase,
    val postUpdateMyInfoUseCase: PostUpdateMyInfoUseCase
) : BaseTestViewModel<MyPageEditState>() {

    private var isNetworkCall:Boolean = false
    private var cameraTempImageUri: Uri? = null

    private val profileImageStateFlow: MutableStateFlow<String?> = MutableStateFlow("")
    private val originProfileStateFlow : MutableStateFlow<String?> = MutableStateFlow("")
    private var nicknameStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private var introduceStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val originNicknameStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val originIntroduceStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val introduceCountStateFlow : MutableStateFlow<SpannableString> = MutableStateFlow(SpannableString(""))
    private val nicknameDescriptionStateFlow : MutableStateFlow<String> = MutableStateFlow("")
    private val nicknameIsActiveStateFlow: MutableStateFlow<Boolean?> = MutableStateFlow(false)
    private val nicknameIsChangedStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isSaveButtonEnableStateFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val uiState: MyPageEditState = MyPageEditState(
        profileImage = profileImageStateFlow.asStateFlow(),
        originProfile = originProfileStateFlow.asStateFlow(),
        nickname = nicknameStateFlow,
        introduce = introduceStateFlow,
        originNickname = originNicknameStateFlow.asStateFlow(),
        originIntroduce = originIntroduceStateFlow.asStateFlow(),
        introduceCount = introduceCountStateFlow.asStateFlow(),
        nicknameDescription = nicknameDescriptionStateFlow.asStateFlow(),
        nicknameIsActive = nicknameIsActiveStateFlow.asStateFlow(),
        nicknameIsChanged = nicknameIsChangedStateFlow.asStateFlow(),
        isSaveButtonEnable = isSaveButtonEnableStateFlow.asStateFlow()
    )

    fun onInitProfile() {
        viewModelScope.launch {
            profileImageStateFlow.update { PlubUser.info.profileImage }
            originProfileStateFlow.update { PlubUser.info.profileImage }
            nicknameStateFlow.update { PlubUser.info.nickname }
            originNicknameStateFlow.update { PlubUser.info.nickname }
            introduceStateFlow.update { PlubUser.info.introduce }
            originIntroduceStateFlow.update { PlubUser.info.introduce }
            introduceCountStateFlow.update { getIntroduceCountSpannableString(PlubUser.info.introduce.length.toString()) }
        }
        onTextChangedAfter()
    }

    fun onTextChangedAfter() {
        val nickname = uiState.nickname.value
        if(nickname == PlubUser.info.nickname){
            handleNicknameCheckSuccess(true)
        }else{
            fetchNicknameCheck(nickname)
        }
    }

    fun fetchNicknameCheck(nickname: String) {
        isNetworkCall = true
        viewModelScope.launch {
            val request = NicknameCheckRequestVo(nickname, this)
            getNicknameCheckUseCase(request).collect { state ->
                inspectUiState(state, ::handleNicknameCheckSuccess, { _, individual ->
                    handleNicknameCheckError(individual as NicknameError)
                })
            }
        }
    }

    fun onClickProfileImage() {
        checkPermission {
            emitEventFlow(MyPageEditEvent.ShowSelectImageBottomSheetDialog)
        }
    }

    fun onClickImageMenuItemType(type: DialogMenuItemType) {
        when(type) {
            DialogMenuItemType.CAMERA_IMAGE -> {
                cameraTempImageUri = imageUtil.getUriFromTempFileInExternalDir().also {
                    emitEventFlow(MyPageEditEvent.GoToCamera(it))
                }
            }
            DialogMenuItemType.ALBUM_IMAGE -> emitEventFlow(MyPageEditEvent.GoToAlbum)
            else -> defaultImage()
        }
    }

    private fun checkPermission(onSuccess:() -> Unit) {
        PermissionManager.createGetImagePermission(onSuccess)
    }

    fun onSelectImageFromAlbum(uri: Uri) {
        emitCropImageAndOptimizeEvent(uri)
    }

    fun onTakeImageFromCamera() {
        cameraTempImageUri?.let { uri ->
            emitCropImageAndOptimizeEvent(uri)
        }
    }

    private fun emitCropImageAndOptimizeEvent(uri: Uri) {
        emitEventFlow(
            MyPageEditEvent.CropImageAndOptimize(
                imageUtil.getCropImageOptions(uri)
            )
        )
    }

    fun proceedCropImageResult(result: CropImageView.CropResult) {
        if (result.isSuccessful) {
            result.uriContent?.let { uri ->
                val file = imageUtil.uriToOptimizeImageFile(uri)
                if (file != null) {
                    uploadProfileFile(file)
                }
            }
        }
    }

    fun onClickNicknameDeleteButton() {
        updateNickname("")
        onTextChangedAfter()
    }

    private fun defaultImage() {
        viewModelScope.launch {
            profileImageStateFlow.update { null }
        }
    }

    private fun handleNicknameCheckSuccess(isAvailableNickname: Boolean) {
        isNetworkCall = false
        updateNicknameState(isAvailableNickname, R.string.sign_up_profile_compose_nickname_available_description)
    }

    private fun handleNicknameCheckError(nicknameError: NicknameError) {
        isNetworkCall = false
        when (nicknameError) {
            is NicknameError.HasSpecialCharacter -> updateNicknameState(false, R.string.sign_up_profile_compose_nickname_special_character_description)
            is NicknameError.HasBlankNickname -> updateNicknameState(false, R.string.sign_up_profile_compose_nickname_blank_description)
            is NicknameError.DuplicatedNickname -> updateNicknameState(false, R.string.sign_up_profile_compose_nickname_duplicated_description)
            is NicknameError.EmptyNickname -> updateNicknameState(null, R.string.sign_up_profile_compose_nickname_empty_description)
            else -> Unit
        }
    }

    private fun updateNicknameState(isActiveNickname: Boolean?, nicknameDescriptionRes: Int) {
        viewModelScope.launch {
            nicknameIsActiveStateFlow.update { isActiveNickname }
            nicknameIsChangedStateFlow.update { uiState.nickname.value != uiState.originNickname.value }
            nicknameDescriptionStateFlow.update { resourceProvider.getString(nicknameDescriptionRes) }
        }
    }

    private fun uploadProfileFile(file: File) {
        viewModelScope.launch {
            if(uiState.profileImage.value.isNullOrEmpty())
                postUploadFileUseCase(UploadFileRequestVo(UploadFileType.PROFILE, file)).collect{
                    inspectUiState(it, ::handleUploadImageSuccess, individualErrorCallback = { _, individual ->
                        handleImageError(individual as ImageError)
                    })
                }
            else
                uiState.profileImage.value?.let{
                    postChangeFileUseCase(ChangeFileRequestVo(UploadFileType.PROFILE,
                        it, file)).collect{
                        inspectUiState(it, ::handleUploadImageSuccess, individualErrorCallback = { _, individual ->
                            handleImageError(individual as ImageError)
                        })
                    }
                }

        }
    }

    private fun handleImageError(imageError: ImageError){
        when(imageError){
            is ImageError.Common -> {}
            is ImageError.FailDelete -> TODO()
            is ImageError.FailUpload -> emitEventFlow(ArchiveEvent.FailUpload)
        }
    }

    private fun handleUploadImageSuccess(state : UploadFileResponseVo){
        viewModelScope.launch {
            profileImageStateFlow.update { state.fileUrl }
        }
    }

    private fun updateNickname(nickname: String) {
        viewModelScope.launch {
            nicknameStateFlow.update { nickname }
        }
    }

    fun onIntroChangedAfter() {
        val introduce: String = uiState.introduce.value
        updateIntroduceState(introduce)
    }

    private fun updateIntroduceState(introduce:String) {
        viewModelScope.launch {
            introduceCountStateFlow.update { getIntroduceCountSpannableString(introduce.length.toString()) }
        }
    }

    private fun getIntroduceCountSpannableString(introduceLength: String): SpannableString {
        val introduceCountString = resourceProvider.getString(R.string.sign_up_more_info_introduce_count,introduceLength)
        val introduceCountColor = resourceProvider.getColor(R.color.color_363636)
        return SpannableString(introduceCountString).apply {
            setSpan(ForegroundColorSpan(introduceCountColor),0,introduceLength.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    fun saveChangedNickName(){
        emitEventFlow(MyPageEditEvent.ShowDialog)
    }

    fun saveChangedOnlyIntro() {
        updateMyInfo()
    }

    fun onClickBackButton(){
        emitEventFlow(MyPageEditEvent.GoToBack)
    }

    fun updateMyInfo(){
        val request = UpdateMyInfoRequestVo(
            nickname = uiState.nickname.value,
            introduce = uiState.introduce.value,
            profileImageUrl = uiState.profileImage.value
        )
        viewModelScope.launch {
            postUpdateMyInfoUseCase(request).collect{
                inspectUiState(it , ::handleUpdateMyInfoSuccess, individualErrorCallback = { _, individual ->
                    handleAccountError(individual as AccountError)
                })
            }
        }
    }

    private fun handleAccountError(accountError: AccountError) {
        when (accountError) {
            is AccountError.AlreadyInactiveAccount -> TODO()
            AccountError.Common -> TODO()
            is AccountError.DuplicatedEmail -> TODO()
            is AccountError.DuplicatedNickname -> TODO()
            is AccountError.InvalidNickname -> TODO()
            is AccountError.InvalidSocialType -> TODO()
            is AccountError.NicknameChangeLimit -> TODO()
            is AccountError.NotFoundAccount -> TODO()
            is AccountError.RoleAccess -> TODO()
            is AccountError.SelfReport -> TODO()
            is AccountError.SuspendedAccount -> TODO()
        }
    }

    private fun handleUpdateMyInfoSuccess(vo : MyInfoResponseVo){
        PlubUser.updateInfo(vo.copy(
            isReceivedPushNotification = PlubUser.info.isReceivedPushNotification
        ))
        emitEventFlow(MyPageEditEvent.GoToBack)
    }

    fun updateButtonState(){
        val enable = uiState.introduce.value.isNotEmpty() && uiState.nickname.value.isNotEmpty() &&
                ((uiState.introduce.value != uiState.originIntroduce.value) || (uiState.nickname.value != uiState.originNickname.value) || (uiState.profileImage.value != uiState.originProfile.value)) && uiState.nicknameIsActive.value == true
        viewModelScope.launch{
            isSaveButtonEnableStateFlow.update { enable }
        }
    }

}