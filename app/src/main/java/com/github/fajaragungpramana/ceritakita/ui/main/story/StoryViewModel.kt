package com.github.fajaragungpramana.ceritakita.ui.main.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.fajaragungpramana.ceritakita.data.domain.preference.PreferenceUseCase
import com.github.fajaragungpramana.ceritakita.data.domain.story.StoryUseCase
import com.github.fajaragungpramana.ceritakita.data.extension.onResultListener
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import com.github.fajaragungpramana.ceritakita.ui.state.StoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val mStoryUseCase: StoryUseCase,
    private val mPreferenceUseCase: PreferenceUseCase
) : ViewModel(), IStoryEvent {

    private val _storyState by lazy { Channel<StoryState>() }
    val storyState: Flow<StoryState>
        get() = _storyState.receiveAsFlow()

    override fun getStories(storyRequest: StoryRequest): Job = viewModelScope.launch {
        mStoryUseCase.getStories(storyRequest).onResultListener(
            onSuccess = { data ->
                data?.collectLatest { _storyState.send(StoryState.OnStorySuccess(it)) }
            },
            onFailure = { _, _ ->
            },
            onError = {
                _storyState.send(StoryState.OnStoryFailure(it.message))
            }
        )
    }

    override fun logout(): Job = viewModelScope.launch {
        mPreferenceUseCase.clear()
    }

}