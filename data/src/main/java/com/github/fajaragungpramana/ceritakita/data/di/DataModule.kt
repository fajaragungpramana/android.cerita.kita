package com.github.fajaragungpramana.ceritakita.data.di

import android.content.Context
import android.content.SharedPreferences
import com.github.fajaragungpramana.ceritakita.data.constant.DataConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideDataStore(): SharedPreferences {
        val context = Data.context

        return if (context != null)
            context.getSharedPreferences(DataConstant.Preferences.AUTH, Context.MODE_PRIVATE)
        else
            throw NullPointerException("Data.context is null.")
    }

}