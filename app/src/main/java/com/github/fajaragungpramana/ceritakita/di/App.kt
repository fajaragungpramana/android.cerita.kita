package com.github.fajaragungpramana.ceritakita.di

import android.app.Application
import com.github.fajaragungpramana.ceritakita.data.di.Data
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Data.context = applicationContext
    }

}