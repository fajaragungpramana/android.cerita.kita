package com.github.fajaragungpramana.ceritakita.ui.state

import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Register

sealed class RegisterState {

    data class OnRegisterLoading(val isLoading: Boolean) : RegisterState()
    data class OnRegisterSuccess(val register: Register?) : RegisterState()
    data class OnRegisterFailure(val message: String?) : RegisterState()

}