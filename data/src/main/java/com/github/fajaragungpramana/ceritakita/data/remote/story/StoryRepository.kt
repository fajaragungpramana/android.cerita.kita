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

class StoryRepository @Inject constructor(private val mStoryDataSource: IStoryDataSource) :
    IStoryRepository {

    override suspend fun getStories(storyRequest: StoryRequest): AppResult<Flow<PagingData<Story>>> =
        connection {
            AppResult.OnSuccess(
                Pager(PagingConfig(pageSize = storyRequest.size)) {
                    StoryDataSource(mStoryDataSource).apply { init(storyRequest) }
                }.flow
            )
        }

}