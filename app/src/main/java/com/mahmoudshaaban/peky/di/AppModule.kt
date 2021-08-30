package com.mahmoudshaaban.peky.di

import android.content.Context
import com.fabirt.roka.core.data.network.client.RecipesApiClient
import com.fabirt.roka.core.data.network.services.RecipeService
import com.mahmoudshaaban.peky.core.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideRecipeService(): RecipeService = RecipesApiClient.createRecipeService()


    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = AppDatabase.createDatabase(context)

    @Provides
    @Singleton
    fun provideRecipeDao(database: AppDatabase) = database.recipeDao()

}