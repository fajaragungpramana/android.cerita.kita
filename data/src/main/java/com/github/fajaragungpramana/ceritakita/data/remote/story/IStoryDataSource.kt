package com.github.fajaragungpramana.ceritakita.data.remote.story

import com.github.fajaragungpramana.ceritakita.data.constant.DataConstant
import com.github.fajaragungpramana.ceritakita.data.remote.story.entity.StoryEntity
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface IStoryDataSource {

    @GET(DataConstant.Http.Route.STORIES)
    suspend fun getStories(@QueryMap storyRequest: StoryRequest): Response<StoryEntity>

    @POST(DataConstant.Http.Route.STORIES)
    suspend fun setStories(@Body requestBody: RequestBody): Response<StoryEntity>

}