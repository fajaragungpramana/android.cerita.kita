package com.github.fajaragungpramana.ceritakita.ui.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.domain.auth.AuthInteractor
import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Login
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mAuthInteractor: AuthInteractor

    private lateinit var mLoginViewModel: LoginViewModel

    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        mLoginViewModel = LoginViewModel(mAuthInteractor)
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
    fun `loginSuccessfully`() = runTest {
        val request = AuthRequest(email = "fajar100500@gmail.com", password = "fajar123")
        val expected = AppResult.OnSuccess<Flow<Login>?>(flow {
            emit(
                Login(
                    responseMessage = "This is example message",
                    token = "This is example token"
                )
            )
        }.flowOn(Dispatchers.IO))
        `when`(mAuthInteractor.login(request)).thenReturn(expected)
        Assert.assertTrue(mAuthInteractor.login(request) is AppResult.OnSuccess)

        Assert.assertNotNull(mLoginViewModel.login(request))
    }

}