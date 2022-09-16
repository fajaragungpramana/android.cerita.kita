package com.github.fajaragungpramana.ceritakita.data.local.preference.di

import android.content.SharedPreferences
import com.github.fajaragungpramana.ceritakita.data.domain.preference.PreferenceInteractor
import com.github.fajaragungpramana.ceritakita.data.domain.preference.PreferenceUseCase
import com.github.fajaragungpramana.ceritakita.data.local.preference.PreferenceDataSource
import com.github.fajaragungpramana.ceritakita.data.local.preference.PreferenceRepository
import com.github.fajaragungpramana.ceritakita.data.local.preference.IPreferenceDataSource
import com.github.fajaragungpramana.ceritakita.data.local.preference.IPreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object PreferenceModule {

    @Provides
    fun provideDataSource(sharedPreference: SharedPreferences): IPreferenceDataSource =
        PreferenceDataSource(sharedPreference)

    @Provides
    fun provideRepository(preferenceDataSource: PreferenceDataSource): IPreferenceRepository =
        PreferenceRepository(preferenceDataSource)

    @Provides
    fun provideUseCase(preferenceRepository: PreferenceRepository): PreferenceUseCase =
        PreferenceInteractor(preferenceRepository)

}