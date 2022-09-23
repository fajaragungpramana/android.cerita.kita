package com.github.fajaragungpramana.ceritakita.data.local.preference

import com.github.fajaragungpramana.ceritakita.data.app.AppResult
import com.github.fajaragungpramana.ceritakita.data.extension.connection
import com.github.fajaragungpramana.ceritakita.data.local.preference.entity.PreferenceEntity
import com.github.fajaragungpramana.ceritakita.data.local.preference.model.Preference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PreferenceRepository @Inject constructor(private val mAuthPreferenceDataSource: IPreferenceDataSource) :
    IPreferenceRepository {

    override suspend fun set(data: Preference): AppResult<Flow<Unit>?> = connection {
        AppResult.OnSuccess(flow {
            emit(mAuthPreferenceDataSource.set(PreferenceEntity.mapObject(data)))
        }.flowOn(Dispatchers.IO))
    }

    override suspend fun get(): AppResult<Flow<Preference>?> = connection {
        AppResult.OnSuccess(flow {
            emit(Preference.mapObject(mAuthPreferenceDataSource.get()))
        }.flowOn(Dispatchers.IO))
    }

}