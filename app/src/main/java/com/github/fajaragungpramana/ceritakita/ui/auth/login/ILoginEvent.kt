package com.github.fajaragungpramana.ceritakita.ui.auth.login

import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import kotlinx.coroutines.Job

interface ILoginEvent {

    fun login(authRequest: AuthRequest): Job

}