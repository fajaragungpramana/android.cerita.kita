package com.github.fajaragungpramana.ceritakita.ui.state

import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Login

sealed class LoginState {

    data class OnLoginLoading(val isLoading: Boolean?) : LoginState()
    data class OnLoginSuccess(val login: Login?) : LoginState()
    data class OnLoginFailure(val message: String?) : LoginState()

}