package com.github.fajaragungpramana.ceritakita.data.domain.story

import androidx.paging.PagingData
import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.remote.story.IStoryRepository
import com.github.fajaragungpramana.ceritakita.data.remote.story.model.Story
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import javax.inject.Inject

class StoryInteractor @Inject constructor(private val mStoryRepository: IStoryRepository) :
    StoryUseCase {

    override suspend fun getStories(storyRequest: StoryRequest): AppResult<Flow<PagingData<Story>>> =
        mStoryRepository.getStories(storyRequest)

    override suspend fun getStoryLocations(storyRequest: StoryRequest): AppResult<Flow<List<Story>>?> =
        mStoryRepository.getStoryLocations(storyRequest)

    override suspend fun setStories(requestBody: RequestBody): AppResult<Flow<Story>?> =
        mStoryRepository.setStories(requestBody)

}