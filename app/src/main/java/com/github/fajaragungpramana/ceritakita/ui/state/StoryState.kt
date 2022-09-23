package com.github.fajaragungpramana.ceritakita.ui.state

import androidx.paging.PagingData
import com.github.fajaragungpramana.ceritakita.data.remote.story.model.Story

sealed class StoryState {

    data class OnStoryLoading(val isLoading: Boolean) : StoryState()
    data class OnStorySuccess(val data: PagingData<Story>) : StoryState()
    data class OnStoryFailure(val message: String?) : StoryState()

}