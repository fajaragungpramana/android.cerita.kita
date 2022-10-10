package com.github.fajaragungpramana.ceritakita.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fajaragungpramana.ceritakita.data.domain.auth.AuthUseCase
import com.github.fajaragungpramana.ceritakita.data.extension.flowAsValue
import com.github.fajaragungpramana.ceritakita.data.extension.onResultListener
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import com.github.fajaragungpramana.ceritakita.ui.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val mAuthUseCase: AuthUseCase) : ViewModel(),
    ILoginEvent {

    private val _loginState by lazy { Channel<LoginState>() }
    val loginState: Flow<LoginState>
        get() = _loginState.receiveAsFlow()

    override fun login(authRequest: AuthRequest): Job = viewModelScope.launch {
        _loginState.send(LoginState.OnLoginLoading(true))
        mAuthUseCase.login(authRequest).onResultListener(
            onSuccess = {
                _loginState.send(LoginState.OnLoginLoading(false))

                _loginState.send(LoginState.OnLoginSuccess(it?.flowAsValue()))
            },
            onFailure = { _, data ->
                _loginState.send(LoginState.OnLoginLoading(false))

                _loginState.send(LoginState.OnLoginFailure(data?.flowAsValue()?.responseMessage))
            },
            onError = {
                _loginState.send(LoginState.OnLoginLoading(false))

                _loginState.send(LoginState.OnLoginFailure(it.message))
            }
        )
    }

}