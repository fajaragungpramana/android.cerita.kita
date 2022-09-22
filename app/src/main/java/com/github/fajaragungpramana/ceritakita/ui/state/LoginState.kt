package com.github.fajaragungpramana.ceritakita.ui.state

sealed class LoginState {

    data class OnLoginLoading(val isLoading: Boolean?) : LoginState()
    object OnLoginSuccess : LoginState()
    data class OnLoginFailure(val message: String?) : LoginState()

}