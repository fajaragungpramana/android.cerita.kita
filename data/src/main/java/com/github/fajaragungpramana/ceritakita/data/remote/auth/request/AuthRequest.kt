package com.github.fajaragungpramana.ceritakita.data.remote.auth.request

import com.github.fajaragungpramana.ceritakita.data.constant.DataConstant
import com.github.fajaragungpramana.ceritakita.data.extension.removeNulls

data class AuthRequest(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null
) : Map<String, Any> by mapOf(
    DataConstant.Http.Param.NAME to name,
    DataConstant.Http.Param.EMAIL to email,
    DataConstant.Http.Param.PASSWORD to password
).removeNulls()