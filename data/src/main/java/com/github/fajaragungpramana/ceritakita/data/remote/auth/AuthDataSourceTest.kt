package com.github.fajaragungpramana.ceritakita.data.remote.auth

import com.github.fajaragungpramana.ceritakita.data.remote.auth.entity.LoginEntity
import com.github.fajaragungpramana.ceritakita.data.remote.auth.entity.RegisterEntity
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import retrofit2.Response

class AuthDataSourceTest : IAuthDataSource {

    override suspend fun login(authRequest: AuthRequest): Response<LoginEntity> =
        Response.success(dummyLoginResponse)

    override suspend fun register(authRequest: AuthRequest): Response<RegisterEntity> =
        Response.success(dummyRegisterResponse)

    companion object {

        val dummyLoginResponse = LoginEntity(
            error = false,
            message = "success",
            loginResult = LoginEntity.Result(
                userId = "user-p84KXjvOVGpSpfQh",
                name = "fajar agung",
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXA4NEtYanZPVkdwU3BmUWgiLCJpYXQiOjE2Njc3ODk2OTB9.4S2FB8ASMHfLSoW0DRa0FQvLSWNsRyIfmfPHEpFBF6w"
            )
        )

        val dummyRegisterResponse = RegisterEntity(
            error = false,
            message = "User Created"
        )

    }

}