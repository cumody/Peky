package com.mahmoudshaaban.peky.core.data.providers

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.mahmoudshaaban.peky.core.constants.K
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreImpl @Inject constructor(
    @ApplicationContext context: Context
) : IDataStoreProvider {

    private val dataStore = context.createDataStore(K.SETTINGS_DATA_STORE_NAME)
    private val onBoardingDidShow = preferencesKey<Boolean>(K.ONBOARDING_DID_SHOW_KEY)


    override suspend fun readOnBoardingDidShow(): Boolean {
        val flow = dataStore.data.map { preferences ->
            preferences[onBoardingDidShow] ?: false

        }
        return flow.first()
    }

    override suspend fun writeOnBoardingDidShow() {
        dataStore.edit { preferences ->
            preferences[onBoardingDidShow] = true
        }
    }
}