package com.github.fajaragungpramana.ceritakita.data.local.preference

import com.github.fajaragungpramana.ceritakita.data.local.preference.entity.PreferenceEntity
import kotlinx.coroutines.flow.Flow

interface IPreferenceDataSource {

    fun set(data: PreferenceEntity)

    fun get(): PreferenceEntity

}