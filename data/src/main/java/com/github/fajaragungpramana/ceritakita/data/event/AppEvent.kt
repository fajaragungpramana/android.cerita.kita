package com.github.fajaragungpramana.ceritakita.data.event

sealed class AppEvent {
    object ForceLogout : AppEvent()
}
