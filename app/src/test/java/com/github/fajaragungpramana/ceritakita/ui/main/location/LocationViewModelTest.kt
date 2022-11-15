package com.github.fajaragungpramana.ceritakita.ui.main.location

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.domain.story.StoryInteractor
import com.github.fajaragungpramana.ceritakita.data.remote.story.model.Story
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LocationViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mStoryInteractor: StoryInteractor

    private lateinit var mLocationViewModel: LocationViewModel

    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        mLocationViewModel = LocationViewModel(mStoryInteractor)
    }

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `storyWithLocationSuccessfully`() = runTest {
        val request = StoryRequest(page = 1, location = 1)
        val expected = AppResult.OnSuccess<Flow<List<Story>>?>(flow {
            emit(
                arrayListOf(
                    Story(
                        id = "1",
                        latitude = 0.0,
                        longitude = 0.0
                    ),
                    Story(
                        id = "2",
                        latitude = 0.0,
                        longitude = 0.0
                    )
                )
            )
        }.flowOn(Dispatchers.IO))
        `when`(mStoryInteractor.getStoryLocations(request)).thenReturn(expected)
        Assert.assertTrue(mStoryInteractor.getStoryLocations(request) is AppResult.OnSuccess)

        Assert.assertNotNull(mLocationViewModel.getStoryLocations())
    }

}