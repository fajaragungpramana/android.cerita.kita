package com.github.fajaragungpramana.ceritakita.data.remote.auth.model

import com.github.fajaragungpramana.ceritakita.data.remote.auth.entity.LoginEntity

data class Login(

    val responseMessage: String? = null,
    val token: String? = null

) {
    companion object {

        fun mapObject(loginEntity: LoginEntity?) = Login(
            responseMessage = loginEntity?.message,
            token = loginEntity?.loginResult?.token
        )

    }
}