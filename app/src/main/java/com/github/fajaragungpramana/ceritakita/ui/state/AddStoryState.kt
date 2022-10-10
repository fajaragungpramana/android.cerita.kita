package com.github.fajaragungpramana.ceritakita.ui.state

import com.github.fajaragungpramana.ceritakita.data.remote.story.model.Story

sealed class AddStoryState {

    data class OnAddStoryLoading(val isLoading: Boolean) : AddStoryState()
    data class OnAddStorySuccess(val story: Story?) : AddStoryState()
    data class OnAddStoryFailure(val message: String?) : AddStoryState()

}