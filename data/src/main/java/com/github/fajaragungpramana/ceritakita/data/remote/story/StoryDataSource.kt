package com.github.fajaragungpramana.ceritakita.data.remote.story

import com.github.fajaragungpramana.ceritakita.data.app.AppPagingDataSource
import com.github.fajaragungpramana.ceritakita.data.remote.story.model.Story
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import javax.inject.Inject

class StoryDataSource @Inject constructor(private val mStoryDataSource: IStoryDataSource) :
    AppPagingDataSource<Story>() {

    private lateinit var mStoryRequest: StoryRequest

    fun init(storyRequest: StoryRequest) {
        mStoryRequest = storyRequest
    }

    override suspend fun onLoadSuccess(position: Int): LoadResult<Int, Story> {
        val response = mStoryDataSource.getStories(mStoryRequest.copy(page = position))
        val listStory = Story.mapList(response.body())

        return LoadResult.Page(
            data = listStory,
            prevKey = if (position == 1) null else position,
            nextKey = if (listStory.isEmpty()) null else position + 1
        )
    }

    override fun onLoadFailure(position: Int, e: Exception): LoadResult<Int, Story> {
        return LoadResult.Error(e)
    }

}