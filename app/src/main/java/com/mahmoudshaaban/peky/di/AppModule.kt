package com.mahmoudshaaban.peky.di

import com.fabirt.roka.core.data.network.client.RecipesApiClient
import com.fabirt.roka.core.data.network.services.RecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideRecipeService(): RecipeService = RecipesApiClient.createRecipeService()

}