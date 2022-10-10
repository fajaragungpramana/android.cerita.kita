package com.github.fajaragungpramana.ceritakita.data.local.preference.entity

import com.github.fajaragungpramana.ceritakita.data.local.preference.model.Preference

data class PreferenceEntity(
    val isBoarding: Boolean? = null,
    val isLogin: Boolean? = null,
    val token: String? = null
) {
    companion object {
        const val IS_BOARDING = "ceritakita.preferences.auth.is_boarding"
        const val IS_LOGIN = "ceritakita.preferences.auth.is_login"
        const val TOKEN = "ceritakita.preferences.auth.token"

        fun mapObject(preference: Preference) = PreferenceEntity(
            isBoarding = preference.isBoarding,
            isLogin = preference.isLogin,
            token = preference.token
        )
    }
}