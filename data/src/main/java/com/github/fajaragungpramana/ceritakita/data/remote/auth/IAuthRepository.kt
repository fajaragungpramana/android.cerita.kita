package com.github.fajaragungpramana.ceritakita.data.remote.auth

import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Login
import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Register
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {

    suspend fun login(authRequest: AuthRequest): AppResult<Flow<Login>?>

    suspend fun register(authRequest: AuthRequest): AppResult<Flow<Register>?>

}