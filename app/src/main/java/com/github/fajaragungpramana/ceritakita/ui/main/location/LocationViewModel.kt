package com.github.fajaragungpramana.ceritakita.ui.main.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fajaragungpramana.ceritakita.data.domain.story.StoryUseCase
import com.github.fajaragungpramana.ceritakita.data.extension.flowAsValue
import com.github.fajaragungpramana.ceritakita.data.extension.onResultListener
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import com.github.fajaragungpramana.ceritakita.ui.state.LocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val mStoryUseCase: StoryUseCase) : ViewModel(),
    ILocationEvent {

    private val _locationState by lazy { Channel<LocationState>() }
    val locationState: Flow<LocationState>
        get() = _locationState.receiveAsFlow()

    override fun getStoryLocations(): Job = viewModelScope.launch {
        _locationState.send(LocationState.OnLocationStoryLoading(isLoading = true))
        mStoryUseCase.getStoryLocations(StoryRequest(page = 1, location = 1)).onResultListener(
            onSuccess = {
                _locationState.send(LocationState.OnLocationStoryLoading(isLoading = false))
                _locationState.send(LocationState.OnLocationStorySuccess(it?.flowAsValue()))
            },
            onFailure = { _, _ ->
                _locationState.send(LocationState.OnLocationStoryLoading(isLoading = false))
                _locationState.send(LocationState.OnLocationStoryFailure(message = null))
            },
            onError = {
                _locationState.send(LocationState.OnLocationStoryLoading(isLoading = false))
                _locationState.send(LocationState.OnLocationStoryFailure(message = it.message))
            }
        )
    }

}