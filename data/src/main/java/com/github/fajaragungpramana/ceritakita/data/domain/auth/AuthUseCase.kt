package com.github.fajaragungpramana.ceritakita.data.domain.auth

import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Login
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    suspend fun login(authRequest: AuthRequest): AppResult<Flow<Login>?>

}