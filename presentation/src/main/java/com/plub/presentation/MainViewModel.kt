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

    private val _userinfo = MutableLiveData<List<User>>()
    val userinfo: LiveData<List<User>> = _userinfo

    fun getUserInfo(owner: String) {
        getUserUseCase(owner, viewModelScope) {
            _userinfo.value = it
        }
    }

    fun login(loginId: String, loginPassword: String, owner : String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = loginUseCase.login(loginId, loginPassword, owner)
            result
                .onSuccess { user ->
                    val (id, pass) = (user.name to user.password.toString())
                    _loginState.value = LoginState.Success
                }
                .onFailure {
                    _loginState.value = LoginState.Fail("에러메세지")
                }
        }

    }

}