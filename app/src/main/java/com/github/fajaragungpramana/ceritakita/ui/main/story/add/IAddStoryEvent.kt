package com.github.fajaragungpramana.ceritakita.ui.main.story.add

import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import kotlinx.coroutines.Job

interface IAddStoryEvent {

    fun setStories(storyRequest: StoryRequest): Job

}