package com.github.fajaragungpramana.ceritakita.data.local.preference

import android.content.SharedPreferences
import androidx.core.content.edit
import com.github.fajaragungpramana.ceritakita.data.local.preference.entity.PreferenceEntity
import javax.inject.Inject

class PreferenceDataSource @Inject constructor(private val mSharedPreference: SharedPreferences) :
    IPreferenceDataSource {

    override fun set(data: PreferenceEntity) {
        mSharedPreference.edit {
            putBoolean(PreferenceEntity.IS_BOARDING, data.isBoarding ?: false)
            putBoolean(PreferenceEntity.IS_LOGIN, data.isLogin ?: false)
            putString(PreferenceEntity.TOKEN, data.token)
        }
    }

    override fun get() = PreferenceEntity(
        isBoarding = mSharedPreference.getBoolean(PreferenceEntity.IS_BOARDING, false),
        isLogin = mSharedPreference.getBoolean(PreferenceEntity.IS_LOGIN, false),
        token = mSharedPreference.getString(PreferenceEntity.TOKEN, null)
    )

    override fun clear() = mSharedPreference.edit().clear().apply()

}