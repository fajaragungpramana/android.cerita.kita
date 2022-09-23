package com.github.fajaragungpramana.ceritakita.ui.auth.register

import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import kotlinx.coroutines.Job

interface IRegisterEvent {

    fun register(authRequest: AuthRequest): Job

}