package com.github.fajaragungpramana.ceritakita.ui.auth.boarding

import kotlinx.coroutines.Job

interface IBoardingEvent {

    fun getListBoarding(): Job

}