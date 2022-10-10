package com.github.fajaragungpramana.ceritakita.data.local.preference.model

import com.github.fajaragungpramana.ceritakita.data.local.preference.entity.PreferenceEntity

data class Preference(
    val isBoarding: Boolean? = null,
    val isLogin: Boolean? = null,
    val token: String? = null
) {
    companion object {
        fun mapObject(preferenceEntity: PreferenceEntity?) = Preference(
            isBoarding = preferenceEntity?.isBoarding,
            isLogin = preferenceEntity?.isLogin,
            token = preferenceEntity?.token
        )
    }
}