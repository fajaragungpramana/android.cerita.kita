package com.github.fajaragungpramana.ceritakita.ui.state

import com.github.fajaragungpramana.ceritakita.data.local.preference.model.Preference

sealed class PreferenceState {

    data class PreferenceLoading(val isLoading: Boolean?) : PreferenceState()
    data class PreferenceSuccess(val isInsert: Boolean, val preference: Preference?) : PreferenceState()
    data class PreferenceFailure(val message: String?) : PreferenceState()

}