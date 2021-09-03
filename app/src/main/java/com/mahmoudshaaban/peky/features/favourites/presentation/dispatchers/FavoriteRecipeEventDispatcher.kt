package com.mahmoudshaaban.peky.features.favourites.presentation.dispatchers

import android.view.View
import com.mahmoudshaaban.peky.features.favorites.domain.model.FavoriteRecipe

interface FavoriteRecipeEventDispatcher {
    fun onFavoriteRecipePressed(recipe: FavoriteRecipe, view: View)

    fun onFavoriteRecipeLongPressed(recipe: FavoriteRecipe)
}