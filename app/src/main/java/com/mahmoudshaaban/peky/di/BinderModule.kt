package com.mahmoudshaaban.peky.di

import com.mahmoudshaaban.peky.core.data.providers.DataStoreImpl
import com.mahmoudshaaban.peky.core.data.providers.IDataStoreProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
abstract class BinderModule {

    @Binds
    abstract fun bindDataStoreProvider(
        dataStoreImpl: DataStoreImpl
    ): IDataStoreProvider

}