package com.plub.presentation.ui.main.profile.setting

import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.viewModelScope
import com.canhub.cropper.CropImageView
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
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MyPageSettingViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val imageUtil: ImageUtil,
    val getNicknameCheckUseCase: GetNicknameCheckUseCase,
    val postUploadFileUseCase: PostUploadFileUseCase,
    val postChangeFileUseCase: PostChangeFileUseCase,
    val postUpdateMyInfoUseCase: PostUpdateMyInfoUseCase
) : BaseViewModel<MyPageSettingState>(MyPageSettingState()) {

    private var isNetworkCall:Boolean = false
    private var cameraTempImageUri: Uri? = null

    fun onInitProfile() {
        updateUiState { ui ->
            ui.copy(
                profileImage = PlubUser.info.profileImage,
                originProfile = PlubUser.info.profileImage,
                nickname = PlubUser.info.nickname,
                originNickname = PlubUser.info.nickname,
                introduce = PlubUser.info.introduce,
                originIntroduce = PlubUser.info.introduce,
                introduceCount = getIntroduceCountSpannableString(PlubUser.info.introduce.length.toString())
            )
        }
        onTextChangedAfter()
    }

    fun onTextChangedAfter() {
        val nickname = uiState.value.nickname
        if(nickname == PlubUser.info.nickname){
            handleNicknameCheckError(NicknameError.EmptyNickname(""))
        }else{
            fetchNicknameCheck(nickname)
        }
    }

    fun fetchNicknameCheck(nickname: String) {
        isNetworkCall = true
        viewModelScope.launch {
            val request = NicknameCheckRequestVo(nickname, this)
            getNicknameCheckUseCase(request).collect { state ->
                inspectUiState(state, ::handleNicknameCheckSuccess) { _, individual ->
                    handleNicknameCheckError(individual as NicknameError)
                }
            }
        }
    }

    fun onClickProfileImage() {
        checkPermission {
            emitEventFlow(MyPageSettingEvent.ShowSelectImageBottomSheetDialog)
        }
    }

    fun onClickImageMenuItemType(type: DialogMenuItemType) {
        when(type) {
            DialogMenuItemType.CAMERA_IMAGE -> {
                cameraTempImageUri = imageUtil.getUriFromTempFileInExternalDir().also {
                    emitEventFlow(MyPageSettingEvent.GoToCamera(it))
                }
            }
            DialogMenuItemType.ALBUM_IMAGE -> emitEventFlow(MyPageSettingEvent.GoToAlbum)
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
            MyPageSettingEvent.CropImageAndOptimize(
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
        updateUiState { uiState ->
            uiState.copy(
                profileImage = null
            )
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
        updateUiState { uiState ->
            uiState.copy(
                nicknameIsActive = isActiveNickname,
                nicknameIsChanged = uiState.nickname != uiState.originNickname,
                nicknameDescription = resourceProvider.getString(nicknameDescriptionRes)
            )
        }
    }

    private fun uploadProfileFile(file: File) {
        viewModelScope.launch {
            if(uiState.value.profileImage.isNullOrEmpty())
                postUploadFileUseCase(UploadFileRequestVo(UploadFileType.PROFILE, file)).collect{
                    inspectUiState(it, ::handleUploadImageSuccess)
                }
            else
                uiState.value.profileImage?.let{
                    postChangeFileUseCase(ChangeFileRequestVo(UploadFileType.PROFILE,
                        it, file)).collect{
                        inspectUiState(it, ::handleUploadImageSuccess)
                    }
                }

        }
    }

    private fun handleUploadImageSuccess(state : UploadFileResponseVo){
        updateUiState { uiState ->
            uiState.copy(
                profileImage = state.fileUrl
            )
        }
    }

    private fun updateNickname(nickname: String) {
        updateUiState { uiState ->
            uiState.copy(
                nickname = nickname
            )
        }
    }

    fun onIntroChangedAfter() {
        val introduce: String = uiState.value.introduce
        updateIntroduceState(introduce)
    }

    private fun updateIntroduceState(introduce:String) {
        updateUiState { uiState ->
            uiState.copy(
                introduceCount = getIntroduceCountSpannableString(introduce.length.toString())
            )
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
        emitEventFlow(MyPageSettingEvent.ShowDialog)
    }

    fun saveChangedOnlyIntro() {
        updateMyInfo()
    }

    fun onClickBackButton(){
        emitEventFlow(MyPageSettingEvent.GoToBack)
    }

    fun updateMyInfo(){
        val request = UpdateMyInfoRequestVo(
            nickname = uiState.value.nickname,
            introduce = uiState.value.introduce,
            profileImageUrl = uiState.value.profileImage
        )
        viewModelScope.launch {
            postUpdateMyInfoUseCase(request).collect{
                inspectUiState(it , ::handleUpdateMyInfoSuccess)
            }
        }
    }

    private fun handleUpdateMyInfoSuccess(vo : MyInfoResponseVo){
        PlubUser.updateInfo(vo)
        emitEventFlow(MyPageSettingEvent.GoToBack)
    }

}