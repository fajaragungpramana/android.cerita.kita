package com.github.fajaragungpramana.ceritakita.data.remote.auth

import com.github.fajaragungpramana.ceritakita.data.constant.DataConstant
import com.github.fajaragungpramana.ceritakita.data.remote.auth.entity.LoginEntity
import com.github.fajaragungpramana.ceritakita.data.remote.auth.entity.RegisterEntity
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthDataSource {

    @POST(DataConstant.Http.Route.LOGIN)
    suspend fun login(@Body authRequest: AuthRequest): Response<LoginEntity>

    @POST(DataConstant.Http.Route.REGISTER)
    suspend fun register(@Body authRequest: AuthRequest): Response<RegisterEntity>

}