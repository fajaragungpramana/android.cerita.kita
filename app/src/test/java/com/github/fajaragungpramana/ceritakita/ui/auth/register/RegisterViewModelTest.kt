package com.github.fajaragungpramana.ceritakita.ui.auth.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.domain.auth.AuthInteractor
import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Register
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mAuthInteractor: AuthInteractor

    private lateinit var mRegisterViewModel: RegisterViewModel

    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        mRegisterViewModel = RegisterViewModel(mAuthInteractor)
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
    fun `registerSuccessfully`() = runTest {
        val request = AuthRequest(
            name = "Fajar Agung Pramana",
            email = "fajar100500@gmail.com",
            password = "fajar123"
        )
        val expected = AppResult.OnSuccess<Flow<Register>?>(
            flow { emit(Register(responseMessage = "This is example message.")) }.flowOn(Dispatchers.IO)
        )
        `when`(mAuthInteractor.register(request)).thenReturn(expected)
        Assert.assertTrue(mAuthInteractor.register(request) is AppResult.OnSuccess)
    }

}