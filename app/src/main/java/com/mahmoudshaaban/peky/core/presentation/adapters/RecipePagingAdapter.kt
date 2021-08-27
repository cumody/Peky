package com.mahmoudshaaban.peky.core.presentation.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.mahmoudshaaban.peky.core.domain.model.Recipe
import com.mahmoudshaaban.peky.core.presentation.RecipeViewHolder
import com.mahmoudshaaban.peky.core.presentation.dispatchers.RecipeEventDispatcher

class RecipePagingAdapter(
private val eventDispatcher: RecipeEventDispatcher
) : PagingDataAdapter<Recipe, RecipeViewHolder>(RecipeViewHolder.RecipeComparator) {
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder.create(parent, eventDispatcher)
    }
}