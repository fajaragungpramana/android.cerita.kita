package com.github.fajaragungpramana.ceritakita.ui.state

import com.github.fajaragungpramana.ceritakita.data.remote.story.model.Story

sealed class LocationState {

    data class OnLocationStoryLoading(val isLoading: Boolean): LocationState()
    data class OnLocationStorySuccess(val listLocation: List<Story>?): LocationState()
    data class OnLocationStoryFailure(val message: String?): LocationState()

}