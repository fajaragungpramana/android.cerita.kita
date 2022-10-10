package com.github.fajaragungpramana.ceritakita.ui.loading

import kotlinx.coroutines.Job

interface ILoadingEvent {

    fun getPreference(): Job

}