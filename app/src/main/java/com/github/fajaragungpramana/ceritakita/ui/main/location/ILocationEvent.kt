package com.github.fajaragungpramana.ceritakita.ui.main.location

import kotlinx.coroutines.Job

interface ILocationEvent {

    fun getStoryLocations(): Job

}