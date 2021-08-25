package com.mahmoudshaaban.peky.core.data.providers

interface IDataStoreProvider {

    suspend fun readOnBoardingDidShow() : Boolean

    suspend fun writeOnBoardingDidShow()
}