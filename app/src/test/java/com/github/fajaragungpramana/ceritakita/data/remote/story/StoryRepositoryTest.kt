package com.github.fajaragungpramana.ceritakita.data.remote.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.fajaragungpramana.ceritakita.data.extension.flowAsValue
import com.github.fajaragungpramana.ceritakita.data.extension.onResultListener
import com.github.fajaragungpramana.ceritakita.data.remote.story.model.Story
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mStoryDataSource: IStoryDataSource

    private lateinit var mStoryRepository: StoryRepository

    @Before
    fun setUp() {
        mStoryDataSource = StoryDataSourceTest()

        mStoryRepository = StoryRepository(mStoryDataSource)
    }

    @Test
    fun `list stories`() = runTest {
        val request = StoryRequest(page = 1)
        val actual = mStoryRepository.getStoriesTest(request)

        actual.onResultListener(
            onSuccess = {
                val listStory = it?.flowAsValue()

                Assert.assertNotNull(listStory)
                Assert.assertEquals(
                    listStory, Story.mapList(StoryDataSourceTest.dummyStoryList)
                )
            },
            onFailure = { _, _ ->

            },
            onError = {

            }
        )
    }

}