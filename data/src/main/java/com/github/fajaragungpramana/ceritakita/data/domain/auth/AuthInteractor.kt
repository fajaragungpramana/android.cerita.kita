package com.github.fajaragungpramana.ceritakita.data.domain.auth

import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.remote.auth.IAuthRepository
import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Login
import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Register
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthInteractor @Inject constructor(private val mAuthRepository: IAuthRepository) :
    AuthUseCase {

    override suspend fun login(authRequest: AuthRequest): AppResult<Flow<Login>?> =
        mAuthRepository.login(authRequest)

    override suspend fun register(authRequest: AuthRequest): AppResult<Flow<Register>?> =
        mAuthRepository.register(authRequest)

}