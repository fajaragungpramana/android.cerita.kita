package com.github.fajaragungpramana.ceritakita.data.remote.story

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.extension.connection
import com.github.fajaragungpramana.ceritakita.data.extension.responseAsFlow
import com.github.fajaragungpramana.ceritakita.data.remote.story.model.Story
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
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

    override suspend fun getStoryLocations(storyRequest: StoryRequest): AppResult<Flow<List<Story>>?> =
        connection {
            mStoryDataSource.getStories(storyRequest).responseAsFlow { Story.mapList(it) }
        }

    override suspend fun setStories(requestBody: RequestBody): AppResult<Flow<Story>?> =
        connection {
            mStoryDataSource.setStories(requestBody).responseAsFlow { Story.mapObject(it) }
        }

    suspend fun getStoriesTest(storyRequest: StoryRequest): AppResult<Flow<List<Story>>?> =
        StoryDataSourceTest().getStories(storyRequest)
            .responseAsFlow { Story.mapList(StoryDataSourceTest.dummyStoryList) }

}