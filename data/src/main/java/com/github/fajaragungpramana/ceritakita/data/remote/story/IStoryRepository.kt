package com.github.fajaragungpramana.ceritakita.data.remote.story

import androidx.paging.PagingData
import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.remote.story.model.Story
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import kotlinx.coroutines.flow.Flow

interface IStoryRepository {

    suspend fun getStories(storyRequest: StoryRequest): AppResult<Flow<PagingData<Story>>>

}