package com.mahmoudshaaban.peky.features.favourites.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mahmoudshaaban.peky.features.favorites.domain.model.FavoriteRecipe
import com.mahmoudshaaban.peky.features.favourites.presentation.dispatchers.FavoriteRecipeEventDispatcher
import com.mahmoudshaaban.peky.features.favourites.presentation.viewholders.FavoriteRecipeViewHolder

class FavoritesAdapter(
    private val eventDispatcher: FavoriteRecipeEventDispatcher,
    var isSelecting: Boolean
) : ListAdapter<FavoriteRecipe, FavoriteRecipeViewHolder>(FavoriteRecipeViewHolder.FavoriteRecipeComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipeViewHolder {
        return FavoriteRecipeViewHolder.from(parent, eventDispatcher)
    }

    override fun onBindViewHolder(holder: FavoriteRecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe, isSelecting)
    }
}