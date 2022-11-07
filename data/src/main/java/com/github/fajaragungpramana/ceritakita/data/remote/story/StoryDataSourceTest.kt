package com.github.fajaragungpramana.ceritakita.data.remote.story

import com.github.fajaragungpramana.ceritakita.data.remote.story.entity.StoryEntity
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import okhttp3.RequestBody
import retrofit2.Response

class StoryDataSourceTest : IStoryDataSource {

    override suspend fun getStories(storyRequest: StoryRequest): Response<StoryEntity> =
        Response.success(dummyStoryList)

    override suspend fun setStories(requestBody: RequestBody): Response<StoryEntity> =
        Response.success(dummySetStory)

    companion object {

        val dummyStoryList = StoryEntity(
            error = false,
            message = "Stories fetched successfully",
            listStory = listOf(
                StoryEntity.Result(
                    id = "story-FvU4u0Vp2S3PMsFg",
                    name = "Dimas",
                    description = "Lorem Ipsum",
                    photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                    createdAt = "2022-01-08T06:34:18.598Z",
                    lat = -10.212,
                    lon = -16.002
                )
            )
        )

        val dummySetStory = StoryEntity(
            error = false,
            message = "success"
        )

    }

}