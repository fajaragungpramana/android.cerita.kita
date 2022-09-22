package com.github.fajaragungpramana.ceritakita.data.remote.auth.model

import com.github.fajaragungpramana.ceritakita.data.remote.auth.entity.RegisterEntity

data class Register(

    val responseMessage: String? = null

) {
    companion object {

        fun mapObject(registerEntity: RegisterEntity?) = Register(
            responseMessage = registerEntity?.message
        )

    }
}