package com.plub.presentation.ui.sign.profileCompose

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.plub.domain.error.NicknameError
import com.plub.domain.model.enums.DialogMenuItemType
import com.plub.domain.model.vo.signUp.profile.ProfileComposeVo
import com.plub.domain.usecase.GetNicknameCheckUseCase
import com.plub.presentation.R
import com.plub.presentation.base.BaseViewModel
import com.plub.presentation.state.ProfileComposePageState
import com.plub.presentation.util.ImageUtil
import com.plub.presentation.util.PermissionManager
import com.plub.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileComposeViewModel @Inject constructor(
    val resourceProvider: ResourceProvider,
    val imageUtil: ImageUtil,
    val getNicknameCheckUseCase: GetNicknameCheckUseCase,
) : BaseViewModel<ProfileComposePageState>(ProfileComposePageState()) {

    private val _showSelectImageBottomSheetDialog = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val showSelectImageBottomSheetDialog: SharedFlow<Unit> = _showSelectImageBottomSheetDialog.asSharedFlow()

    private val _moveToNextPage = MutableSharedFlow<ProfileComposeVo>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val moveToNextPage: SharedFlow<ProfileComposeVo> = _moveToNextPage.asSharedFlow()

    private val _goToAlbum = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val goToAlbum: SharedFlow<Unit> = _goToAlbum.asSharedFlow()

    private val _goToCamera = MutableSharedFlow<Uri>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val goToCamera: SharedFlow<Uri> = _goToCamera.asSharedFlow()

    private var isNetworkCall:Boolean = false
    private var cameraTempImageUri:Uri? = null

    init {
        onTextChangedAfter()
    }

    fun onInitProfileComposeVo(profileComposeVo: ProfileComposeVo) {
        if(uiState.value != ProfileComposePageState()) return

        updateUiState { ui ->
            ui.copy(
                profileComposeVo = profileComposeVo
            )
        }
        onTextChangedAfter()
    }

    fun onTextChangedAfter() {
        val nickname: String = uiState.value.profileComposeVo.nickname
        fetchNicknameCheck(nickname)
    }

    fun fetchNicknameCheck(nickname: String) {
        isNetworkCall = true
        viewModelScope.launch {
            getNicknameCheckUseCase(nickname).collect { state ->
                inspectUiState(state, ::handleNicknameCheckSuccess) { _, individual ->
                    handleNicknameCheckError(individual as NicknameError)
                }
            }
        }
    }

    fun onClickProfileImage() {
        checkPermission {
            viewModelScope.launch {
                _showSelectImageBottomSheetDialog.emit(Unit)
            }
        }
    }

    fun onClickImageMenuItemType(type:DialogMenuItemType) {
        when(type) {
            DialogMenuItemType.CAMERA_IMAGE -> viewModelScope.launch {
                cameraTempImageUri = resourceProvider.getUriFromTempFile().also {
                    _goToCamera.emit(it)
                }
            }
            DialogMenuItemType.ALBUM_IMAGE -> viewModelScope.launch { _goToAlbum.emit(Unit) }
            else -> defaultImage()
        }
    }

    private fun checkPermission(onSuccess:() -> Unit) {
        PermissionManager.createGetImagePermission(onSuccess)
    }

    fun onSelectImageFromAlbum(uri:Uri) {
        val realPath = imageUtil.getRealPathFromURI(uri)
        val file = File(realPath)
        updateProfileFile(file)
    }

    fun onTakeImageFromCamera() {
        cameraTempImageUri?.path?.let {
            val file = File(it)
            updateProfileFile(file)
        }
    }

    fun onClickNextButton() {
        viewModelScope.launch {
            _moveToNextPage.emit(uiState.value.profileComposeVo)
        }
    }

    fun onClickNicknameDeleteButton() {
        updateNickname("")
        onTextChangedAfter()
    }

    private fun defaultImage() {
        updateProfileFile(null)
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
                isNextButtonEnable = isNextButtonEnable(isActiveNickname),
                nicknameIsActive = isActiveNickname,
                nicknameDescription = resourceProvider.getString(nicknameDescriptionRes)
            )
        }
    }

    private fun updateProfileFile(file: File?) {
        updateUiState { uiState ->
            uiState.copy(
                profileComposeVo = uiState.profileComposeVo.copy(
                    profileFile = file
                )
            )
        }
    }

    private fun updateNickname(nickname: String) {
        updateUiState { uiState ->
            uiState.copy(
                profileComposeVo = uiState.profileComposeVo.copy(
                    nickname = nickname
                )
            )
        }
    }

    private fun isNextButtonEnable(isActiveNickname:Boolean?): Boolean {
        return isActiveNickname == true && !isNetworkCall
    }
}