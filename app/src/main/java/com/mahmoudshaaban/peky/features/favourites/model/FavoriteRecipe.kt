package com.mahmoudshaaban.peky.features.favorites.domain.model

import com.mahmoudshaaban.peky.core.domain.model.Recipe

data class FavoriteRecipe(
    val data: Recipe,
    val isSelected: Boolean
) {
    val id: Int get() = data.id
}