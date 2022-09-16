package com.github.fajaragungpramana.ceritakita.data.remote.auth.di

import com.github.fajaragungpramana.ceritakita.data.domain.auth.AuthInteractor
import com.github.fajaragungpramana.ceritakita.data.domain.auth.AuthUseCase
import com.github.fajaragungpramana.ceritakita.data.local.preference.PreferenceDataSource
import com.github.fajaragungpramana.ceritakita.data.remote.auth.AuthRepository
import com.github.fajaragungpramana.ceritakita.data.remote.auth.IAuthDataSource
import com.github.fajaragungpramana.ceritakita.data.remote.auth.IAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object AuthModule {

    @Provides
    fun provideDataSource(retrofit: Retrofit): IAuthDataSource =
        retrofit.create(IAuthDataSource::class.java)

    @Provides
    fun provideRepository(
        authDataSource: IAuthDataSource,
        preferenceDataSource: PreferenceDataSource
    ): IAuthRepository = AuthRepository(authDataSource, preferenceDataSource)

    @Provides
    fun provideUseCase(authRepository: AuthRepository): AuthUseCase =
        AuthInteractor(authRepository)

}