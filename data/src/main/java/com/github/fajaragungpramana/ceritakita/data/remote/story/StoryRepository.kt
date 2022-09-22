package com.github.fajaragungpramana.ceritakita.data.remote.story

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.extension.connection
import com.github.fajaragungpramana.ceritakita.data.remote.story.model.Story
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoryRepository @Inject constructor(private val mStoryDataSource: StoryDataSource) :
    IStoryRepository {

    override suspend fun getStories(storyRequest: StoryRequest): AppResult<Flow<PagingData<Story>>> =
        connection {
            mStoryDataSource.init(storyRequest)

            AppResult.OnSuccess(Pager(PagingConfig(pageSize = storyRequest.size)) { mStoryDataSource }.flow)
        }

}