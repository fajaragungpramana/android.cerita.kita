package com.github.fajaragungpramana.ceritakita.ui.main.story

import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import kotlinx.coroutines.Job

interface IStoryEvent {

    fun getStories(storyRequest: StoryRequest): Job

}