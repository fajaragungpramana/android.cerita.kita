package com.github.fajaragungpramana.ceritakita.ui.main.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.domain.preference.PreferenceInteractor
import com.github.fajaragungpramana.ceritakita.data.domain.story.StoryInteractor
import com.github.fajaragungpramana.ceritakita.data.remote.story.StoryDataSource
import com.github.fajaragungpramana.ceritakita.data.remote.story.request.StoryRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mStoryInteractor: StoryInteractor

    @Mock
    private lateinit var mPreferenceInteractor: PreferenceInteractor

    @Mock
    private lateinit var mStoryDatasource: StoryDataSource

    private lateinit var mStoryViewModel: StoryViewModel

    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        mStoryViewModel = StoryViewModel(mStoryInteractor, mPreferenceInteractor)
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
    fun `checkPagingResultIsSuccess`() = runTest {
        val request = StoryRequest(page = 1)
        val expected = AppResult.OnSuccess(Pager(PagingConfig(pageSize = 15)) {
            mStoryDatasource.apply { init(request) }
        }.flow)
        `when`(mStoryInteractor.getStories(request)).thenReturn(expected)
        Assert.assertTrue(mStoryInteractor.getStories(request) is AppResult.OnSuccess)

        Assert.assertNotNull(mStoryViewModel.getStories(request))
    }

}