package com.github.fajaragungpramana.ceritakita.data.domain.preference

import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.local.preference.IPreferenceRepository
import com.github.fajaragungpramana.ceritakita.data.local.preference.model.Preference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferenceInteractor @Inject constructor(private val mPreferenceRepository: IPreferenceRepository) :
    PreferenceUseCase {

    override suspend fun set(data: Preference): AppResult<Flow<Unit>?> =
        mPreferenceRepository.set(data)

    override suspend fun get(): AppResult<Flow<Preference>?> =
        mPreferenceRepository.get()

    override suspend fun clear() = mPreferenceRepository.clear()

}