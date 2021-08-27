package com.mahmoudshaaban.peky.core.presentation.dispatchers

import android.view.View
import com.mahmoudshaaban.peky.core.domain.model.Recipe

interface RecipeEventDispatcher {
    fun onRecipePressed(recipe: Recipe, view: View)
}