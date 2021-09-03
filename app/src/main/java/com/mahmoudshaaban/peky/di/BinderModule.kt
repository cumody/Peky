package com.mahmoudshaaban.peky.di

import com.mahmoudshaaban.peky.core.data.providers.DataStoreImpl
import com.mahmoudshaaban.peky.core.data.providers.IDataStoreProvider
import com.mahmoudshaaban.peky.core.domain.repository.RecipeRepository
import com.mahmoudshaaban.peky.core.domain.repository.RecipeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
abstract class BinderModule {

    @Binds
    abstract fun bindDataStoreProvider(
        dataStoreImpl: DataStoreImpl
    ): IDataStoreProvider

    @Binds
    abstract fun bindRecipeRepository(
        recipeRepositoryImpl: RecipeRepositoryImpl
    ): RecipeRepository

}