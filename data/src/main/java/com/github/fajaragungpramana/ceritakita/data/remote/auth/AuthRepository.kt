package com.github.fajaragungpramana.ceritakita.data.remote.auth

import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.extension.connection
import com.github.fajaragungpramana.ceritakita.data.extension.responseAsFlow
import com.github.fajaragungpramana.ceritakita.data.local.preference.IPreferenceDataSource
import com.github.fajaragungpramana.ceritakita.data.local.preference.entity.PreferenceEntity
import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Login
import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Register
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val mAuthDataSource: IAuthDataSource,
    private val mPreferenceDataSource: IPreferenceDataSource
) : IAuthRepository {

    override suspend fun login(authRequest: AuthRequest): AppResult<Flow<Login>?> = connection {
        mAuthDataSource.login(authRequest).responseAsFlow {
            val token = it?.loginResult?.token
            if (token != null) mPreferenceDataSource.set(
                PreferenceEntity(
                    isLogin = true,
                    token = token
                )
            )

            Login.mapObject(it)
        }

    }

    override suspend fun register(authRequest: AuthRequest): AppResult<Flow<Register>?> =
        connection {
            mAuthDataSource.register(authRequest).responseAsFlow { Register.mapObject(it) }
        }

}