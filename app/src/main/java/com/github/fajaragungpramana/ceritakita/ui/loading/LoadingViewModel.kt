package com.github.fajaragungpramana.ceritakita.ui.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fajaragungpramana.ceritakita.data.domain.preference.PreferenceUseCase
import com.github.fajaragungpramana.ceritakita.data.extension.flowAsValue
import com.github.fajaragungpramana.ceritakita.data.extension.onResultListener
import com.github.fajaragungpramana.ceritakita.ui.state.PreferenceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(private val mPreferenceUseCase: PreferenceUseCase) :
    ViewModel(), ILoadingEvent {

    private val _preferenceState by lazy { Channel<PreferenceState>() }
    val preferenceState: Flow<PreferenceState>
        get() = _preferenceState.receiveAsFlow()

    override fun getPreference(): Job = viewModelScope.launch {
        mPreferenceUseCase.get().onResultListener(
            onLoading = {
                _preferenceState.send(PreferenceState.PreferenceLoading(it)) },
            onSuccess = {
                _preferenceState.send(
                    PreferenceState.PreferenceSuccess(
                        isInsert = false,
                        preference = it?.flowAsValue()
                    )
                )
            },
            onFailure = { _, _ ->
                _preferenceState.send(PreferenceState.PreferenceFailure(null))
            },
            onError = {
                _preferenceState.send(PreferenceState.PreferenceFailure(it.message))
            }
        )
    }

}