package com.github.fajaragungpramana.ceritakita.data.remote.auth.entity

import com.google.gson.annotations.SerializedName

data class RegisterEntity(

    @SerializedName("error")
    val error: Boolean? = null,

    @SerializedName("message")
    val message: String? = null

)