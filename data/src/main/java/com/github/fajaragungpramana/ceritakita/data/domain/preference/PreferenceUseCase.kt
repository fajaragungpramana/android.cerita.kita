package com.github.fajaragungpramana.ceritakita.data.domain.preference

import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.local.preference.model.Preference
import kotlinx.coroutines.flow.Flow

interface PreferenceUseCase {

    suspend fun set(data: Preference): AppResult<Flow<Unit>?>

    suspend fun get(): AppResult<Flow<Preference>?>

}