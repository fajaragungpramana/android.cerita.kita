package com.github.fajaragungpramana.ceritakita.data.domain.story

import androidx.paging.PagingData
import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.remote.story.IStoryRepository
import com.github.fajaragungpramana.ceritakita.data.remote.story.model.Story
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoryInteractor @Inject constructor(private val mStoryRepository: IStoryRepository) :
    StoryUseCase {

    override suspend fun getStories(storyRequest: StoryRequest): AppResult<Flow<PagingData<Story>>> =
        mStoryRepository.getStories(storyRequest)

}