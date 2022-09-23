package com.github.fajaragungpramana.ceritakita.data.remote.auth.entity

import com.google.gson.annotations.SerializedName

data class LoginEntity(

    @SerializedName("error")
    val error: Boolean? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("loginResult")
    val loginResult: Result? = null

) {

    data class Result(

        @SerializedName("userId")
        val userId: String? = null,

        @SerializedName("name")
        val name: String? = null,

        @SerializedName("token")
        val token: String? = null

    )

}