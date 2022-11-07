package com.github.fajaragungpramana.ceritakita.data.remote.auth

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.fajaragungpramana.ceritakita.data.extension.flowAsValue
import com.github.fajaragungpramana.ceritakita.data.extension.onResultListener
import com.github.fajaragungpramana.ceritakita.data.local.preference.IPreferenceDataSource
import com.github.fajaragungpramana.ceritakita.data.local.preference.PreferenceDataSource
import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Login
import com.github.fajaragungpramana.ceritakita.data.remote.auth.model.Register
import com.github.fajaragungpramana.ceritakita.data.remote.auth.request.AuthRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mSharedPreference: SharedPreferences

    private lateinit var mAuthDataSource: IAuthDataSource
    private lateinit var mPreferenceDataSource: IPreferenceDataSource

    private lateinit var mAuthRepository: AuthRepository

    @Before
    fun setUp() {
        mAuthDataSource = AuthDataSourceTest()
        mPreferenceDataSource = PreferenceDataSource(mSharedPreference)

        mAuthRepository = AuthRepository(mAuthDataSource, mPreferenceDataSource)
    }

    @Test
    fun `when login is successful`() = runTest {
        val request = AuthRequest(email = "fajar100500@gmail.com", password = "fajar123")
        val actual = mAuthRepository.loginTest(request)

        actual.onResultListener(
            onSuccess = {
                val login = it?.flowAsValue()

                Assert.assertNotNull(login)
                Assert.assertEquals(
                    login,
                    Login.mapObject(AuthDataSourceTest.dummyLoginResponse)
                )
            },
            onFailure = { _, _ ->

            },
            onError = {

            }
        )
    }

    @Test
    fun `when register successful`() = runTest {
        val request = AuthRequest(
            name = "Fajar Agung Pramana",
            email = "fajar100500@gmail.com",
            password = "fajar123"
        )
        val actual = mAuthRepository.registerTest(request)

        actual.onResultListener(
            onSuccess = {
                val register = it?.flowAsValue()

                Assert.assertNotNull(register)
                Assert.assertEquals(
                    register,
                    Register.mapObject(AuthDataSourceTest.dummyRegisterResponse)
                )
            },
            onFailure = { _, _ ->

            },
            onError = {

            }
        )
    }

}