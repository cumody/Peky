package com.mahmoudshaaban.peky.core.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudshaaban.peky.core.data.providers.IDataStoreProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class DataStoreViewModel @ViewModelInject constructor(
    private val iDataStore: IDataStoreProvider
) : ViewModel() {

    suspend fun readOnBoardingDidShow() = iDataStore.readOnBoardingDidShow()

    fun writeOnBoardingDidShow() {
        viewModelScope.launch {
            iDataStore.writeOnBoardingDidShow()
        }
    }



}