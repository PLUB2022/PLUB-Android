package com.plub.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.User
import com.plub.presentation.state.LoginState
import kotlinx.coroutines.launch
import usecase.GetUserUseCase
import usecase.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private var _loginState = MutableStateFlow<LoginState>(LoginState.Loading)
    var loginState = _loginState.asStateFlow()

    private var _userinfo = MutableStateFlow<LoginState>(LoginState.Loading)
    var userinfo = _userinfo.asStateFlow()

//    private val _userinfo = MutableLiveData<List<User>>()
//    val userinfo: LiveData<List<User>> = _userinfo

//    fun getUserInfo(owner: String) {
//        getUserUseCase(owner, viewModelScope) {
//            _userinfo.value = it
//        }
//    }

    suspend fun login(loginId: String, loginPassword: String, owner : String) {
        if(loginUseCase.login(loginId, loginPassword, owner)){
            getUserUseCase.getUser(owner)
            //위와 같은 방법으로 유저 데이터 가져오기
        }

    }

    fun getUserInfo(owner : String){
        viewModelScope.launch {
            _userinfo.value = LoginState.Loading
            val result = getUserUseCase.getUser(owner)
            result
                .onSuccess { user ->
                    val (id, pass) = (user.id to user.password.toString())
                    _userinfo.value = LoginState.Success
                }
                .onFailure {
                    _userinfo.value = LoginState.Fail("에러메세지")
                }
        }
    }

}