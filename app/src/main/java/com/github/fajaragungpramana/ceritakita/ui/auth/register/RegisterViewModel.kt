package com.github.fajaragungpramana.ceritakita.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fajaragungpramana.ceritakita.data.domain.auth.AuthUseCase
import com.github.fajaragungpramana.ceritakita.data.extension.flowAsValue
import com.github.fajaragungpramana.ceritakita.data.extension.onResultListener
import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Register
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import com.github.fajaragungpramana.ceritakita.ui.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val mAuthUseCase: AuthUseCase) : ViewModel(),
    IRegisterEvent {

    private val _registerState by lazy { Channel<RegisterState>() }
    val registerState: Flow<RegisterState>
        get() = _registerState.receiveAsFlow()

    override fun register(authRequest: AuthRequest): Job = viewModelScope.launch {
        _registerState.send(RegisterState.OnRegisterLoading(isLoading = true))

        mAuthUseCase.register(authRequest).onResultListener(
            onSuccess = {
                _registerState.send(RegisterState.OnRegisterLoading(isLoading = false))
                _registerState.send(RegisterState.OnRegisterSuccess(it?.flowAsValue()))
            },
            onFailure = { _, data ->
                _registerState.send(RegisterState.OnRegisterLoading(isLoading = false))
                _registerState.send(RegisterState.OnRegisterFailure(data?.flowAsValue()?.responseMessage))
            },
            onError = {
                _registerState.send(RegisterState.OnRegisterLoading(isLoading = false))
                _registerState.send(RegisterState.OnRegisterFailure(it.message))
            }
        )
    }

}